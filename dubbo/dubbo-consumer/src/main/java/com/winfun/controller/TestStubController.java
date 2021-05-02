package com.winfun.controller;

import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试本地存根
 * @author winfun
 * @date 2021/2/1 10:26 上午
 **/
//@RestController
public class TestStubController {

    @DubboReference(lazy = true,check = false,stub = "com.winfun.demo.stub.DubboServiceOneStub")
    private DubboServiceOne dubboServiceOne;

    @GetMapping("/stub/{name}")
    public ApiResult<String> testStub(@PathVariable("name") String name){
        return this.dubboServiceOne.sayHello(name);
    }
}

