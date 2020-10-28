package com.hyf.testDemo.mq.topic;

import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/6
 */
public class Send {

    private static final String exchangeName = "hyf.topic.exchange";
    private static final String routingkeyByLogin = "user.login.info";
    private static final String routingkeyByRegister = "user.register.info";
    private static final String routingkeyByOrder = "order.detail.info";
    private static final String routingkeyByStock = "stock.detail.info";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            channel.exchangeDeclare(exchangeName, "topic");

            String msg1 = "用户张三登陆了";
            String msg2 = "新用户李四注册了";
            String msg3 = "张三买了一台iphone12";
            String msg4 = "iphone12库存减一";

            channel.basicPublish(exchangeName, routingkeyByLogin, null, msg1.getBytes());
            channel.basicPublish(exchangeName, routingkeyByRegister, null, msg2.getBytes());
            channel.basicPublish(exchangeName, routingkeyByOrder, null, msg3.getBytes());
            channel.basicPublish(exchangeName, routingkeyByStock, null, msg4.getBytes());
        }
    }
}
