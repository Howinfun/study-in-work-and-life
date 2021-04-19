package com.hyf.testDemo.mq.rpc;

import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/7
 */
public class Server {

    private static final String requestQueueName = "hyf.rpc.request.queue";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(requestQueueName, false, false, false, null);
        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "utf-8");
            // 处理消息
            String reponse = handleMsg(msg);
            // 将消息的 correlationId 传回去
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();
            channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, reponse.getBytes());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicQos(1);
        channel.basicConsume(requestQueueName, false, callback, consumeTag -> {});

    }

    private static String handleMsg(String msg){
        return msg + "已经被处理了";
    }
}
