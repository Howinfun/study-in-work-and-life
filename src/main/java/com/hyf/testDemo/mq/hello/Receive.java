package com.hyf.testDemo.mq.hello;

import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/6
 */
public class Receive {

    private static final String queueName = "hyf.hello.queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);

        DeliverCallback deliverCallback =  (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(message);
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
