package com.winfun.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
import com.winfun.service.DubboServiceTwo;
import com.winfun.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试自研 Sentinel Rules SDK
 * @author winfun
 * @date 2020/10/29 5:12 下午
 **/
@Slf4j
@RestController
@RequestMapping("/sentinel/rules/sdk")
public class SentinelRulesSDKHelloController {

    public static final String RESOURCE_NAME = "sayHello";
    public static final String RESOURCE_NAME2 = "sayHi";

    @GetMapping("/hello/{name}")
    @SentinelResource(value=RESOURCE_NAME,fallback = "sayHelloFallback",blockHandler = "sayHelloBlock")
    public ApiResult sayHello(@PathVariable("name") final String name){

        return ApiResult.success("hello "+name);
    }

    @GetMapping("/hi/{name}")
    @SentinelResource(value=RESOURCE_NAME2,fallback = "sayHelloFallback",blockHandler = "sayHelloBlock")
    public ApiResult sayHi(@PathVariable("name") final String name){

        return ApiResult.success("hi "+name);
    }

    /**
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
     * @param name
     * @param throwable
     * @return {@link ApiResult<String> }
     */
    public ApiResult<String> sayHelloFallback(final String name, final Throwable throwable){
        log.error("资源：{} 发生异常,message is {}",RESOURCE_NAME,throwable.getMessage());
        return ApiResult.fail("hello exception");
    }

    /**
     * BlockHandler 函数
     * blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException
     * @param name
     * @param e
     * @return {@link ApiResult<String> }
     */
    public ApiResult<String> sayHelloBlock(final String name, final BlockException e){

        if (e instanceof DegradeException){
            log.error("资源：{} 被熔断了,message is {}",RESOURCE_NAME,e.getMessage());
            return ApiResult.fail("hello fallback");
        }else {
            log.error("资源：{} 被流控了",RESOURCE_NAME);
            return ApiResult.fail("hello block");
        }
    }
}
