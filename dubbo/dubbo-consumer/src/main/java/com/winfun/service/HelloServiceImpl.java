package com.winfun.service;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.winfun.controller.SentinelCoreHelloController.RESOURCE_NAME;

/**
 *
 * @author winfun
 * @date 2021/1/22 2:02 下午
 **/
@Slf4j
@Service
public class HelloServiceImpl implements HelloService{

    @Override
    //@SentinelResource(value=RESOURCE_NAME,fallback = "sayHelloFallback",blockHandler = "sayHelloBlock")
    public String sayHello(String name) {
        return "hello "+name;
    }

    /**
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
     * @param name
     * @param throwable
     * @return
     */
    public String sayHelloFallback(final String name, final Throwable throwable){
        log.error("资源：{} 被熔断了,message is {}",RESOURCE_NAME,throwable.getMessage());
        return "fallback";
    }

    /**
     * BlockHandler 函数
     * blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException
     * @param name
     * @param exception
     * @return
     */
    public String sayHelloBlock(final String name, final BlockException exception){
        log.error("资源：{} 被流控了",RESOURCE_NAME);
        return "block";
    }
}
