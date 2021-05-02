package com.winfun.controller;

import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
import com.winfun.service.DubboServiceTwo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Say Hello
 * @author winfun
 * @date 2020/10/29 5:12 下午
 **/
@Slf4j
//@RestController
@RequestMapping("/sentinel/dubbo")
public class SentinelDubboHelloController {

    public static final String DUBBO_INTERFACE_RESOURCE_NAME = "com.winfun.service.DubboServiceOne";
    @DubboReference(check = false,lazy = true,retries = 0,mock = "true")
    private DubboServiceOne dubboServiceOne;
    @DubboReference(check = false,lazy = true,retries = 0)
    private DubboServiceTwo dubboServiceTwo;
    /**
     * 初始化流控规则和熔断规则
     * ps:因为我们没有接入 Sentinel Dashboard，所以得自己在代码里面设置好
     */


    @GetMapping("/hello/{name}")
    public ApiResult sayHello(@PathVariable("name") final String name){
        return this.dubboServiceOne.sayHello(name);
    }

    @GetMapping("/hi/{name}")
    public ApiResult sayHi(@PathVariable("name") final String name){
        return this.dubboServiceTwo.sayHi(name);
    }
}
