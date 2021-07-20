package com.winfun.controller;

import org.apache.dubbo.config.DubboShutdownHook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author winfun
 * @date 2021/7/20 9:05 上午
 **/
@RestController
@RequestMapping("/dubbo")
public class DubboShutdownController {

    @GetMapping("/shutdown")
    public String shutdown(){
        DubboShutdownHook.destroyAll();
        return "success";
    }
}
