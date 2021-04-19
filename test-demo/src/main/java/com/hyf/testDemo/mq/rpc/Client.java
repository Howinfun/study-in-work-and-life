package com.hyf.testDemo.mq.rpc;

import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/7
 */
public class Client {

    private static final String replyQueueName = "hyf.rpc.reply.queue";
    private static final String requestQueueName = "hyf.rpc.request.queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(replyQueueName, false, false, false, null);
        // 阻塞队列
        final BlockingQueue<String> responseQueue = new ArrayBlockingQueue<>(1);

        final String corrId = UUID.randomUUID().toString();
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .replyTo(replyQueueName)
                .correlationId(corrId)
                .build();
        String msg = "客户端消息";
        channel.basicPublish("", requestQueueName, properties, msg.getBytes());


        String ctag = channel.basicConsume(replyQueueName, true, (consumeTag,delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                responseQueue.offer(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumeTag -> {});

        String result = responseQueue.take();
        System.out.println(result);
        // 取消订阅
        channel.basicCancel(ctag);
    }
}
