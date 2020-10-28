package com.hyf.testDemo.mq.publishsubscribe;

import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/6
 */
public class Receive12 {

    private static final String exchangeName = "hyf.ps.exchange";
    private static final String queueName = "hyf.ps.queue1";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName,"fanout");
        channel.queueDeclare(queueName,false, false, false, null);
        channel.queueBind(queueName, exchangeName,"");

        DeliverCallback callback = (s, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };

        channel.basicQos(1);
        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, callback, consumerTag -> {});

    }
}
