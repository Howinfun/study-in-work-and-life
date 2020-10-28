package com.hyf.testDemo.mq.dlx;

import com.alibaba.fastjson.JSON;
import com.hyf.algorithm.抽奖概率.common.Result;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/21
 */
@RestController
@RequestMapping("/deadLetter")
public class DeadLetterController {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private UserMsgMapper userMsgMapper;

    @GetMapping("/sendMsg")
    public Result sendMsg(){

        UserMsg userMsg1 = new UserMsg(1,"15627236666","你好，麻烦充值",1);
        UserMsg userMsg2 = new UserMsg(2,"15627236667","你好，麻烦支付",1);
        UserMsg userMsg3 = new UserMsg(3,"15627236668","你好，麻烦下单",1);
        String msgJson1 = JSON.toJSONString(userMsg1);
        String msgJson2 = JSON.toJSONString(userMsg2);
        String msgJson3 = JSON.toJSONString(userMsg3);

        userMsgMapper.insert(userMsg1);
        userMsgMapper.insert(userMsg2);
        userMsgMapper.insert(userMsg3);

        MessagePostProcessor messagePostProcessor = message -> {
            // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(1 * 1000 * 60 + "");
            return message;
        };
        rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE_NAME,RabbitMQConfig.BUSINESS_QUEUE_ROUTING_KEY,msgJson1,messagePostProcessor);
        rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE_NAME,RabbitMQConfig.BUSINESS_QUEUE_ROUTING_KEY,msgJson2,messagePostProcessor);
        rabbitTemplate.convertAndSend(RabbitMQConfig.BUSINESS_EXCHANGE_NAME,RabbitMQConfig.BUSINESS_QUEUE_ROUTING_KEY,msgJson3,messagePostProcessor);
        return new Result("调用成功");
    }
}
