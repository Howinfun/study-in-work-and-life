package com.hyf.testDemo.mq.topic;

import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/7
 */
public class InfoReceive {

    private static final String exchangeName = "hyf.topic.exchange";
    private static final String bindingKey = "*.*.info";
    private static final String queueName = "hyf.topic.info.queue";

    @SneakyThrows
    public static void main(String[] args){

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "topic");
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, bindingKey);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "utf-8");
            System.out.println("接收到一条info消息："+msg);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicQos(1);
        channel.basicConsume(queueName, false, callback, consumerTag -> {});
    }
}
