package com.hyf.originalArticles.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Howinfun
 * @desc
 * @date 2020/1/15
 */
@RestController
public class TestController {

    @Autowired
    //private RedisTemplate redisTemplate;

    @GetMapping
    public String test(){

        //System.out.println(redisTemplate.getConnectionFactory());
        return "redis";
    }
}
