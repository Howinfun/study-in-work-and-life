package com.github.howinfun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.howinfun.entity.User;
import com.github.howinfun.mapper.UserMapper;
import com.github.howinfun.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: winfun
 * @date: 2021/7/3 12:22 下午
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
