package com.hyf.testDemo.mq.routing;

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
public class LogSend {

    private static final String exchangeName = "hyf.routing.exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        try (Connection connection = factory.newConnection();
              Channel channel = connection.createChannel()){
            // direct 类型的交换器
            channel.exchangeDeclare(exchangeName, "direct");
            String routingKeyByError = "error";
            String routingKeyByInfo = "info";

            for (int i = 0; i <= 10; i++){
                String errorMsg = "错误日志"+i;
                channel.basicPublish(exchangeName, routingKeyByError, false, null, errorMsg.getBytes());
            }

            for (int i = 0; i <= 10; i++){
                String errorMsg = "信息日志"+i;
                channel.basicPublish(exchangeName, routingKeyByInfo, false, null, errorMsg.getBytes());
            }
        }
    }
}
