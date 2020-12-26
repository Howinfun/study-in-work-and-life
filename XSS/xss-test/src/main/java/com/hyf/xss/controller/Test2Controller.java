package com.hyf.xss.controller;

import com.hyf.xss.pojo.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Xss攻击
 * @author winfun
 * @date 2020/12/24 12:30 下午
 **/
@RestController
@RequestMapping("/test2")
public class Test2Controller {

    @GetMapping("/get")
    public String hello(@RequestParam("name") String name,@RequestParam("sex") String sex){
        return name+"-"+sex;
    }

    @PostMapping("/post")
    public String post(@RequestBody Test test){
        return test.toString();
    }

    @PutMapping("/put")
    public String put(Test test){
        return test.toString();
    }

    @GetMapping("/exclude/get")
    public String hello2(@RequestParam("name") String name){
        return name;
    }

    @PostMapping("/exclude/post")
    public String post2(@RequestBody Test test){
        return test.toString();
    }

    @PutMapping("/exclude/put")
    public String put2(Test test){
        return test.toString();
    }
}
