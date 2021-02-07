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
 *
 * @author winfun
 * @date 2021/2/7 11:45 上午
 **/
@Slf4j
@RestController
@RequestMapping("/not/fallback")
public class NotFallbackHelloController {

    @DubboReference(check = false,lazy = true,retries = 0)
    private DubboServiceOne dubboServiceOne;
    @DubboReference(check = false,lazy = true,retries = 0)
    private DubboServiceTwo dubboServiceTwo;


    @GetMapping("/hello/{name}")
    public ApiResult sayHello(@PathVariable("name") final String name){
        return this.dubboServiceOne.sayHello(name);
    }

    @GetMapping("/hi/{name}")
    public ApiResult sayHi(@PathVariable("name") final String name){
        return this.dubboServiceTwo.sayHi(name);
    }
}

