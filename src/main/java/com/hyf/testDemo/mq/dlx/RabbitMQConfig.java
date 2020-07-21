package com.hyf.testDemo.mq.dlx;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/21
 */
@Configuration
public class RabbitMQConfig {

    public static final String BUSINESS_QUEUE_NAME = "business.queue";
    public static final String BUSINESS_EXCHANGE_NAME = "business.exchange";
    public static final String BUSINESS_QUEUE_ROUTING_KEY = "business.routing.key";
    public static final String DEAD_LETTER_EXCHANGE_NAME = "dead.letter.exchange";
    public static final String DEAD_LETTER_QUEUE_NAME = "dead.letter.queue";
    public static final String DEAD_LETTER_QUEUE_ROUTING_KEY = "dead.letter.routing.key";

    /**
     * 声明业务交换器
     * @return
     */
    @Bean("businessExchange")
    public DirectExchange businessExchange(){
        return new DirectExchange(BUSINESS_EXCHANGE_NAME);
    }

    /**
     * 声明业务队列
     * @return
     */
    @Bean("businessQueue")
    public Queue businessQueue(){
        Map<String, Object> args = new HashMap<>(3);
        // 这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_NAME);
        // 这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_ROUTING_KEY);
        return QueueBuilder.durable(BUSINESS_QUEUE_NAME).withArguments(args).build();
    }

    /**
     * 声明业务队列绑定业务交换器，绑定路由键
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding businessBinding(@Qualifier("businessQueue") Queue queue,
                                   @Qualifier("businessExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BUSINESS_QUEUE_ROUTING_KEY);
    }

    /**
     * 声明死信交换器
     * @return
     */
    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE_NAME);
    }

    /**
     * 声明死信队列
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME).build();
    }

    /**
     * 声明死信队列绑定死信交换器，绑定路由键
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding deadLetterBinding(@Qualifier("deadLetterQueue") Queue queue,
                                   @Qualifier("deadLetterExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_QUEUE_ROUTING_KEY);
    }
}
