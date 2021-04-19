package com.winfun.controller;

import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author winfun
 * @date 2021/3/15 5:57 下午
 **/
@Slf4j
@RestController
@RequestMapping("/loadbalance")
public class LoadBalanceController {

    @DubboReference(check = false,lazy = true,loadbalance = "random")
    private DubboServiceOne dubboServiceOne;


    @GetMapping("/hello/{name}")
    public ApiResult sayHello(@PathVariable("name") final String name){

        return this.dubboServiceOne.sayHello(name);
    }
}
