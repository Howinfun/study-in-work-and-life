package com.hyf.testDemo.mq.hello;

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

    private static final String queueName = "hyf.hello.queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = ConnectionFactoryUtils.getFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            // 是否持久化（默认保存在内存，可以持久化到磁盘）
            boolean durable = false;
            // 是否独有（此 Connection 独有，通过其他 Connection 创建的 channel 无法访问此队列）
            boolean exclusive = false;
            // 是否自动删除队列（队列没有消费者时，删除）
            boolean autoDelete = false;
            channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);

            String message = "Hello world3!";
            // 第一个参数是交换器名字，第二个参数是 routingKey（不使用交换器时，为队列名称），第三个参数是消息属性（AMQP.BasicProperties），第四个参数是消息
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println("发布成功");
        }

    }
}
