package com.winfun.controller;

import com.alibaba.fastjson.JSON;
import com.winfun.aop.LogRecordAnno;
import com.winfun.entity.User;
import com.winfun.entity.enums.LogRecordEnum;
import com.winfun.mapper.UserMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


    @PutMapping("/insert")
    @LogRecordAnno(logType = LogRecordEnum.INSERT,
                    mapperName = UserMapper.class,
                    id = "#user.id")
    public String insert(@RequestBody User user){
        userMapper.insert(user);
        return JSON.toJSONString(user);
    }

    @PostMapping("/update")
    @LogRecordAnno(logType = LogRecordEnum.UPDATE,
            mapperName = UserMapper.class,
            id = "#user.id")
    public String update(@RequestBody User user){
        userMapper.updateById(user);
        return JSON.toJSONString(user);
    }

    @DeleteMapping("/delete/{id}")
    @LogRecordAnno(logType = LogRecordEnum.DELETE,
            mapperName = UserMapper.class,
            id = "#id")
    public String delete(@PathVariable("id") String id){
        userMapper.deleteById(id);
        return id;
    }
}
