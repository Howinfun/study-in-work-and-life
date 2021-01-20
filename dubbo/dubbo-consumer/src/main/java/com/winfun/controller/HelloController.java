package com.winfun.controller;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
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
@RequestMapping("/hello")
@RestController
public class HelloController {

    @DubboReference(check = false,lazy = true,retries = 1)
    private DubboServiceOne dubboServiceOne;

    @GetMapping("/{name}")
    public ApiResult sayHello(@PathVariable("name") String name){
        System.out.println("有东西进来了");
        String hello = this.sayHelloByDubbo(name);
        return ApiResult.success(hello);
    }

    @SentinelResource(value = "sayHello",entryType = EntryType.IN)
    private String sayHelloByDubbo(String name){
        String hello = dubboServiceOne.sayHello(name);
        return hello;
    }
}
