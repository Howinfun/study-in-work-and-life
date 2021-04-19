package com.hyf.testDemo.mq.workqueue;

import cn.hutool.core.thread.ThreadUtil;
import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/6
 */
public class Receive2 {

    private static final String queueName = "hyf.work.queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);

        channel.basicQos(1);
        DeliverCallback deliverCallback =  (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            ThreadUtil.sleep(4, TimeUnit.SECONDS);
            System.out.println(message);
            // 是否批量提交
            boolean multiple = false;
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), multiple);
        };

        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {});
    }
}
