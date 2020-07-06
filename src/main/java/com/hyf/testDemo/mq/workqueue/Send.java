package com.hyf.testDemo.mq.workqueue;

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

    private static final String queueName = "hyf.work.queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            channel.queueDeclare(queueName, false, false, false, null);

            for (int i = 0; i<=50; i++){
                String message = "消息"+i;
                channel.basicPublish("", queueName, null, message.getBytes());
                System.out.println("发布消息：" + message);
            }
        }

    }
}
