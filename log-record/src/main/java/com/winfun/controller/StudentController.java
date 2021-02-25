package com.winfun.controller;

import com.winfun.aop.LogRecordAnno;
import com.winfun.entity.User;
import com.winfun.entity.enums.LogRecordEnum;
import com.winfun.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Student Controller
 * @author winfun
 * @date 2020/11/3 4:32 下午
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    UserMapper userMapper;
    @PostMapping("/test")
    @LogRecordAnno(logType = LogRecordEnum.INSERT,
                    mapperName = UserMapper.class,
                    id = "#user.id")
    public String test(@RequestBody User user){
        userMapper.insert(user);
        return user.toString();
    }
}
