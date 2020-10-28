package com.hyf.testDemo.mq.dlx;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;

/**
 * @author Howinfun
 * @desc 监听死信队列
 * @date 2020/7/21
 */
@RabbitListener(queues = {RabbitMQConfig.DEAD_LETTER_QUEUE_NAME})
@Component
public class DeadLetterQueueListener {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserMsgMapper userMsgMapper;

    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void processHandler(String msg, Channel channel, Message message) throws IOException {

        try {
            UserMsg userMsg = JSON.parseObject(new String(message.getBody()), UserMsg.class);
            // 模拟发送短信
            int num = new Random().nextInt(10);
            if (num >5){
                // 发送成功
                // 更新数据库记录
                System.out.println("消息【" + userMsg.getId() + "】发送成功，失败次数：" + userMsg.getFailCount());
                userMsgMapper.update(userMsg);
            }else {
                // 重新发到业务队列中
                int failCount = userMsg.getFailCount()+1;
                if (failCount > 5){
                    System.out.println("消息【"+ userMsg.getId() +"】发送次数已到上线");
                    userMsgMapper.update(userMsg);
                }else {
                    userMsg.setFailCount(failCount);
                    String msgJson = JSON.toJSONString(userMsg);
                    System.out.println("消息【"+ userMsg.getId() +"】发送失败，失败次数为："+ userMsg.getFailCount());
                    userMsgMapper.update(userMsg);
                    MessagePostProcessor messagePostProcessor = message2 -> {
                        // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
                        message2.getMessageProperties().setExpiration(1 * 1000 * 60 + "");
                        return message2;
                    };
                    rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE_NAME,RabbitMQConfig.BUSINESS_QUEUE_ROUTING_KEY,msgJson,messagePostProcessor);
                }
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            System.err.println("消息即将再次返回队列处理...");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
