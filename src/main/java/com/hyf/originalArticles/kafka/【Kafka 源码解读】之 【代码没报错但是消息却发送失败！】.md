#### 一、问题：消息发送失败

虽然在使用资源后关闭资源是非常正常的操作，但是确实我们也是经常会缺少调用 `close()` 关闭资源的的代码，特别是在自己写 demo 的时候。而且，以前写关于文件 IO 的例子时，不写 `close()` 方法确实也不会报错或者出现问题，但是那为啥到了 Kafka 这里，不写就会出现问题呢？

```java
Properties properties = new Properties();
properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
               StringSerializer.class.getName());
properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
               StringSerializer.class.getName());
properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
KafkaProducer<String, String> producer =
    new KafkaProducer<>(properties);
ProducerRecord<String, String> record =
    new ProducerRecord<>(topic, "hello, Kafka!");
try {
    producer.send(record);
} catch (Exception e) {
    e.printStackTrace();
}
//不写它导致消息发送失败
//producer.close();
```



#### 二、猜测

##### 1.首先我们看一下close()方法。

注释：此方法会一直阻塞直到之前所有的发送请求都完成。

```java
/**
     * Close this producer. This method blocks until all previously sent requests complete.
     * This method is equivalent to <code>close(Long.MAX_VALUE, TimeUnit.MILLISECONDS)</code>.
     * <p>
     * <strong>If close() is called from {@link Callback}, a warning message will be logged and close(0, TimeUnit.MILLISECONDS)
     * will be called instead. We do this because the sender thread would otherwise try to join itself and
     * block forever.</strong>
     * <p>
     *
     * @throws InterruptException If the thread is interrupted while blocked
     */
    @Override
    public void close() {
        close(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
```

##### 2.然后再到KafkaProducer中的注释

生产者由一个缓冲区空间池组成，其中保存尚未传输到服务器的记录，以及一个后台I/O线程，该线程负责将这些记录转换为请求并将它们传输到集群。使用后不关闭生产商将泄露这些资源。

```java
/*
* <p>
* The producer consists of a pool of buffer space that holds records that haven't yet been transmitted to the server
* as well as a background I/O thread that is responsible for turning these records into requests and transmitting them
* to the cluster. Failure to close the producer after use will leak these resources.
* <p>
*/
```

##### 3.猜测总结

到这里我们可以稍微总结一下，KafkaProducer 发送消息并不是立刻往 Kafka 中发送，而是先存在一个缓冲区里，然后有一条后台线程去不断地读取消息，然后再往 Kafka 中发送。我们也可以总结一下，之所以不写 close() 方法，我们的 main() 方法中，发送完 main() 方法就执行完了，而此时消息可能只是刚到缓冲区中，还没被后台线程去读取然后发送。

#### 三、验证

下面我们要阅读 KafkaProducer 的源码，来验证上面的总结。

##### 1.KafkaProducer创建

最要关注的是：创建了存放消息的队列，并且创建了一条后台线程，主要是从队列中获取消息，往 kafka 中发送。

```java
KafkaProducer(ProducerConfig config,
              Serializer<K> keySerializer,
              Serializer<V> valueSerializer,
              Metadata metadata,
              KafkaClient kafkaClient) {
    try {
        // ..... 省略掉很多其他代码，主要是关于Producer的配置，例如clientId、序列化和拦截器等等。
        
        // 这里就是存放消息的队列。
        this.accumulator = new RecordAccumulator(logContext,
                                                 config.getInt(ProducerConfig.BATCH_SIZE_CONFIG),
                                                 this.totalMemorySize,
                                                 this.compressionType,
                                                 config.getLong(ProducerConfig.LINGER_MS_CONFIG),
                                                 retryBackoffMs,
                                                 metrics,
                                                 time,
                                                 apiVersions,
                                                 transactionManager);
        // .... 继续省略无关配置
        
        // 这个Sender实现了Runnable，是一条后台线程，处理向Kafka集群发送生产请求的后台线程
        this.sender = new Sender(logContext,
                                 client,
                                 this.metadata,
                                 this.accumulator,
                                 maxInflightRequests == 1,
                                 config.getInt(ProducerConfig.MAX_REQUEST_SIZE_CONFIG),
                                 acks,
                                 retries,
                                 metricsRegistry.senderMetrics,
                                 Time.SYSTEM,
                                 this.requestTimeoutMs,
                                 config.getLong(ProducerConfig.RETRY_BACKOFF_MS_CONFIG),
                                 this.transactionManager,
                                 apiVersions);
        String ioThreadName = NETWORK_THREAD_PREFIX + " | " + clientId;
        this.ioThread = new KafkaThread(ioThreadName, this.sender, true);
        // 启动线程
        this.ioThread.start();
        this.errors = this.metrics.sensor("errors");
        config.logUnused();
        AppInfoParser.registerAppInfo(JMX_PREFIX, clientId, metrics);
        log.debug("Kafka producer started");
    } catch (Throwable t) {
        // call close methods if internal objects are already constructed this is to prevent resource leak. see KAFKA-2121
        close(0, TimeUnit.MILLISECONDS, true);
        // now propagate the exception
        throw new KafkaException("Failed to construct kafka producer", t);
    }
}
```



##### 2.KafkaProducer发送消息

发送消息并不是直接就往 kafka 发送，而是存放到我们上面提及到的队列 accumulator。

```java
/**
  * Asynchronously send a record to a topic. Equivalent to <code>send(record, null)</code>.
  * 异步发送消息记录到指定主题
  * See {@link #send(ProducerRecord, Callback)} for details.
*/
@Override
public Future<RecordMetadata> send(ProducerRecord<K, V> record) {
    return send(record, null);
}

@Override
public Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callback) {
    // intercept the record, which can be potentially modified; this method does not throw exceptions
    ProducerRecord<K, V> interceptedRecord = this.interceptors.onSend(record);
    return doSend(interceptedRecord, callback);
}

/**
 * Implementation of asynchronously send a record to a topic.
 * 实现异步发送消息到对应的主题
*/
private Future<RecordMetadata> doSend(ProducerRecord<K, V> record, Callback callback) {
    TopicPartition tp = null;
    try {
        // ... 省略了一些代码，主要是关于序列化、分区、事务、CallBack等等的配置

        // 下面是往创建KafkaProducer时创建的accumulator里添加消息记录。
        // RecordAccumulator，private final ConcurrentMap<TopicPartition, Deque<ProducerBatch>> batches;是存放消息的变量。
        RecordAccumulator.RecordAppendResult result = accumulator.append(tp, timestamp, serializedKey,
                                                                         serializedValue, headers, interceptCallback, remainingWaitMs);
        if (result.batchIsFull || result.newBatchCreated) {
            log.trace("Waking up the sender since topic {} partition {} is either full or getting a new batch", record.topic(), partition);
            this.sender.wakeup();
        }
        return result.future;
    
    // 下面是异常处理
    } catch (ApiException e) {
        log.debug("Exception occurred during message send:", e);
        if (callback != null)
            callback.onCompletion(null, e);
        this.errors.record();
        this.interceptors.onSendError(record, tp, e);
        return new FutureFailure(e);
    }
    // ....省略一堆异常处理
}
```

##### 3.Sender发送消息

接下来我们得看一下后台线程 Sender 是怎么从队列 accumulator 里面获取消息记录，然后发往 Kafka 的。

```java
/**
 * The main run loop for the sender thread
 * 循环执行
*/
public void run() {
    log.debug("Starting Kafka producer I/O thread.");

    // main loop, runs until close is called
    while (running) {
        try {
            run(time.milliseconds());
        } catch (Exception e) {
            log.error("Uncaught error in kafka producer I/O thread: ", e);
        }
    }

    log.debug("Beginning shutdown of Kafka producer I/O thread, sending remaining records.");

    // okay we stopped accepting requests but there may still be
    // requests in the accumulator or waiting for acknowledgment,
    // wait until these are completed.
    // 我们已停止接受请求(调用了close()方法)，但accumulator中可能仍有请求或等待确认，那么就wait直到这些请求完成
    while (!forceClose && (this.accumulator.hasUndrained() || this.client.inFlightRequestCount() > 0)) {
        try {
            run(time.milliseconds());
        } catch (Exception e) {
            log.error("Uncaught error in kafka producer I/O thread: ", e);
        }
    }
    // 当等待时长用完，要强制关闭时，我们要让所有未完成的批处理失败
    if (forceClose) {
        // We need to fail all the incomplete batches and wake up the threads waiting on
        // the futures.
        log.debug("Aborting incomplete batches due to forced shutdown");
        this.accumulator.abortIncompleteBatches();
    }
    try {
        // 关闭客户端
        this.client.close();
    } catch (Exception e) {
        log.error("Failed to close network client", e);
    }

    log.debug("Shutdown of Kafka producer I/O thread has completed.");
}
```

###### 4.sendProducerData方法

发送数据前的准备

```java
private long sendProducerData(long now) {
        Cluster cluster = metadata.fetch();

        // get the list of partitions with data ready to send
    	// 获取准备发送数据的分区列表
        RecordAccumulator.ReadyCheckResult result = this.accumulator.ready(cluster, now);

        // if there are any partitions whose leaders are not known yet, force metadata update	
    	// 处理没有可用的leader副本的问题，强制更新元数据
        if (!result.unknownLeaderTopics.isEmpty()) {
            // The set of topics with unknown leader contains topics with leader election pending as well as
            // topics which may have expired. Add the topic again to metadata to ensure it is included
            // and request metadata update, since there are messages to send to the topic.
            for (String topic : result.unknownLeaderTopics)
                this.metadata.add(topic);

            log.debug("Requesting metadata update due to unknown leader topics from the batched records: {}", result.unknownLeaderTopics);

            this.metadata.requestUpdate();
        }

        // remove any nodes we aren't ready to send to
    	// 移除还没准备好发送的节点
        Iterator<Node> iter = result.readyNodes.iterator();
        long notReadyTimeout = Long.MAX_VALUE;
        while (iter.hasNext()) {
            Node node = iter.next();
            if (!this.client.ready(node, now)) {
                iter.remove();
                notReadyTimeout = Math.min(notReadyTimeout, this.client.pollDelayMs(node, now));
            }
        }

        // 创建请求
        Map<Integer, List<ProducerBatch>> batches = this.accumulator.drain(cluster, result.readyNodes,
                this.maxRequestSize, now);
        if (guaranteeMessageOrder) {
            // Mute all the partitions drained
            for (List<ProducerBatch> batchList : batches.values()) {
                for (ProducerBatch batch : batchList)
                    this.accumulator.mutePartition(batch.topicPartition);
            }
        }

    	// ..... 省略其他处理
        
    	// 真正发送请求的方法
        sendProduceRequests(batches, now);

        return pollTimeout;
    }
```

###### 5.sendProduceRequest方法

最后是使用 NetworkClient 发送数据到 Kafka 的。

```java
/**
     * Create a produce request from the given record batches
     */
    private void sendProduceRequest(long now, int destination, short acks, int timeout, List<ProducerBatch> batches) {
        if (batches.isEmpty())
            return;

        Map<TopicPartition, MemoryRecords> produceRecordsByPartition = new HashMap<>(batches.size());
        final Map<TopicPartition, ProducerBatch> recordsByPartition = new HashMap<>(batches.size());

        // find the minimum magic version used when creating the record sets
        byte minUsedMagic = apiVersions.maxUsableProduceMagic();
        for (ProducerBatch batch : batches) {
            if (batch.magic() < minUsedMagic)
                minUsedMagic = batch.magic();
        }

        for (ProducerBatch batch : batches) {
            TopicPartition tp = batch.topicPartition;
            MemoryRecords records = batch.records();

            // down convert if necessary to the minimum magic used. In general, there can be a delay between the time
            // that the producer starts building the batch and the time that we send the request, and we may have
            // chosen the message format based on out-dated metadata. In the worst case, we optimistically chose to use
            // the new message format, but found that the broker didn't support it, so we need to down-convert on the
            // client before sending. This is intended to handle edge cases around cluster upgrades where brokers may
            // not all support the same message format version. For example, if a partition migrates from a broker
            // which is supporting the new magic version to one which doesn't, then we will need to convert.
            if (!records.hasMatchingMagic(minUsedMagic))
                records = batch.records().downConvert(minUsedMagic, 0, time).records();
            produceRecordsByPartition.put(tp, records);
            recordsByPartition.put(tp, batch);
        }

        String transactionalId = null;
        if (transactionManager != null && transactionManager.isTransactional()) {
            transactionalId = transactionManager.transactionalId();
        }
        ProduceRequest.Builder requestBuilder = ProduceRequest.Builder.forMagic(minUsedMagic, acks, timeout,
                produceRecordsByPartition, transactionalId);
        RequestCompletionHandler callback = new RequestCompletionHandler() {
            public void onComplete(ClientResponse response) {
                handleProduceResponse(response, recordsByPartition, time.milliseconds());
            }
        };

        String nodeId = Integer.toString(destination);
        ClientRequest clientRequest = client.newClientRequest(nodeId, requestBuilder, now, acks != 0,
                requestTimeoutMs, callback);
        // 最后是利用NetworkClient发送的。
        client.send(clientRequest, now);
        log.trace("Sent produce request to {}: {}", nodeId, requestBuilder);
    }
```

#### 四、最后：

​	如果不写 `Producer.close()` ，确实可能会导致消息的发送失败，而注释中也提醒了我们一定要 `close` 掉生产者，避免资源泄漏。而这其中最主要的原因是 `KafkaProducer` 实现异步发送的逻辑。它是先将消息存放到`RecordAccumulator` 队列中，然后让 `KafkaThread` 线程后台不断地从 `RecordAccumulator` 中读取已准备好发送的消息，最后发送到 `Kafka` 中。而我们的代码中，如果不写 `Producer.close()`，就不会进行超时 `wait`，而当 `main()` 方法执行完后，`KafkaThread` 线程还没来得及从 `RecordAccumulator` 队列中获取消息也跟着被销毁了,所以导致消息最后还是没发送成功。