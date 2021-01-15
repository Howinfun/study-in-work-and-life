package com.winfun.controller;

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

    @DubboReference(check = false,lazy = true,mock = "return null",retries = 1)
    private DubboServiceOne dubboServiceOne;

    @GetMapping("/{name}")
    public ApiResult sayHello(@PathVariable("name") String name){
        String hello = dubboServiceOne.sayHello(name);
        return ApiResult.success(hello);
    }
}
