package com.hyf.testDemo.redis;

import com.hyf.algorithm.抽奖概率.common.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/24
 */
@RestController
@RequestMapping("/redis")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/user")
    @Cacheable(value = "user")
    public Result queryUserList(){
        return new Result(this.userMapper.queryUserList());
    }

    @GetMapping("/user/{id}")
    public Result queryUser(@PathVariable(name = "id") Long id){
        return new Result(this.userMapper.queryUserById(id));
    }

    @PostMapping("/user")
    public Result addUser(@RequestBody User user){
        return new Result(this.userMapper.addUser(user));
    }

    @DeleteMapping("/user/{id}")
    public Result delUser(@PathVariable(name = "id") Long id){
        return new Result(this.userMapper.delUser(id));
    }

    @PutMapping("/user")
    public Result updateUser(@RequestBody User user){
        return new Result(this.userMapper.updateUser(user));
    }

}
