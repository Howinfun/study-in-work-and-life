package com.winfun.controller;

import com.winfun.entity.User;
import com.winfun.pojo.ApiResult;
import com.winfun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Student Controller
 * @author winfun
 * @date 2020/11/3 4:32 下午
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PutMapping("/insert")
    public ApiResult<Object> insert(@RequestBody User user){
        String operator = "system";
        String id = this.userService.insert(user,operator);
        return ApiResult.success(id);
    }

    @PostMapping("/update")
    public ApiResult<Boolean> update(@RequestBody User user){
        String operator = "system";
        Boolean success = this.userService.update(user,operator);
        return ApiResult.success(success);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        String operator = "system";
        Boolean success = this.userService.delete(id,operator);
        return ApiResult.success(success);
    }
}
