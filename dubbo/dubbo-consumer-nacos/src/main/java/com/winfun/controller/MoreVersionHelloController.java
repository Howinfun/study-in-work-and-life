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
@RequestMapping("/more/version")
public class MoreVersionHelloController {


    @DubboReference(version = "1.0.0",timeout = -1)
    private DubboServiceOne dubboServiceOne;

    @GetMapping("/hello/v2/{name}")
    public ApiResult sayHelloV2(@PathVariable("name") final String name){
        return this.dubboServiceOne.sayHello(name);
    }

}

