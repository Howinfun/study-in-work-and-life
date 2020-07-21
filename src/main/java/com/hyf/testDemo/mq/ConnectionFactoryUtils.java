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
        factory.setHost("xxx.xxx.xxx.xxx");
        factory.setVirtualHost("hyf_test");
        factory.setUsername("hyf");
        factory.setPassword("hyf");
        return factory;
    }
}
