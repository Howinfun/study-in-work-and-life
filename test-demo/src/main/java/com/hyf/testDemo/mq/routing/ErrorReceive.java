package com.hyf.testDemo.mq.routing;

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
public class ErrorReceive {

    private static final String exchangeName = "hyf.routing.exchange";
    private static final String queueName = "hyf.routing.error.queue";
    private static final String bindingKey = "error";
    private static final String bindingKey2 = "info";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明 exchange 和 queue
        channel.exchangeDeclare(exchangeName, "direct");
        channel.queueDeclare(queueName, false, false, false, null);

        // 进行绑定
        channel.queueBind(queueName, exchangeName, bindingKey);
        channel.queueBind(queueName, exchangeName, bindingKey2);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(),"utf-8");
            System.out.println("ErrorReceive 接收到" + delivery.getEnvelope().getRoutingKey() + "消息："+message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };

        channel.basicQos(1);
        channel.basicConsume(queueName, false, callback, consumerTag -> {});
    }
}
