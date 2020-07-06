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
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("vhost");
        factory.setUsername("admin");
        factory.setPassword("password");
        return factory;
    }
}
