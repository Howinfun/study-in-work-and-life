package com.hyf.testDemo.mq.publishsubscribe;

import com.hyf.testDemo.mq.ConnectionFactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/6
 */
public class Send {

    private static final String exchangeName = "hyf.ps.exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            // 声明 fanout 类型的交换器
            channel.exchangeDeclare(exchangeName,"fanout");
            for (int i = 0; i <= 10; i++){
                String message = "消息"+i;
                // 直接指定交换器进行消息发布
                channel.basicPublish(exchangeName,"", null, message.getBytes());
            }
        }
    }
}
