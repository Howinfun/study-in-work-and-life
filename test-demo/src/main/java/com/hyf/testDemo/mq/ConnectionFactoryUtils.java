package com.hyf.testDemo.mq;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/6
 */
public class ConnectionFactoryUtils {

    public static ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.172.0.187");
        factory.setVirtualHost("hyf_test");
        factory.setUsername("hyf");
        factory.setPassword("hyf");
        return factory;
    }
}
