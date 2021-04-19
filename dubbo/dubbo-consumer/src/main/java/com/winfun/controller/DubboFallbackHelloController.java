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
 * @date 2021/2/7 11:47 上午
 **/
@Slf4j
@RestController
@RequestMapping("/dubbo/fallback")
public class DubboFallbackHelloController {

    /**
     * 利用 mock实现类 + mock=true 实现服务降级
     */
    @DubboReference(check = false,lazy = true,retries = 0,mock = "true")
    private DubboServiceOne dubboServiceOne;
    /**
     * 直接利用 mock属性设置返回值 实现服务降级
     */
    @DubboReference(check = false,lazy = true,retries = 0,mock = "return {\"code\":1,\"message\":\"服务异常\"}")
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
