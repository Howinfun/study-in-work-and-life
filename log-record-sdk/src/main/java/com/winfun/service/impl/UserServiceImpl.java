package com.winfun.service.impl;

import com.winfun.aop.LogRecordAnno;
import com.winfun.entity.User;
import com.winfun.entity.enums.LogRecordEnum;
import com.winfun.mapper.UserMapper;
import com.winfun.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 *
 * @author winfun
 * @date 2021/2/25 3:58 下午
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 新增用户记录
     * @param user
     * @return
     */
    @LogRecordAnno(logType = LogRecordEnum.INSERT,
            mapperName = UserMapper.class,
            id = "#user.id",
            operator = "#operator")
    @Override
    public String insert(User user,String operator) {
        this.userMapper.insert(user);
        return user.getId();
    }

    /**
     * 更新用户记录
     * @param user
     * @return
     */
    @LogRecordAnno(logType = LogRecordEnum.UPDATE,
            mapperName = UserMapper.class,
            id = "#user.id",
            operator = "#operator")
    @Override
    public Boolean update(User user,String operator) {
        return this.userMapper.updateById(user) > 0;
    }

    /**
     * 删除用户记录
     * @param id
     * @return
     */
    @LogRecordAnno(logType = LogRecordEnum.DELETE,
            mapperName = UserMapper.class,
            id = "#id",
            operator = "#operator")
    @Override
    public Boolean delete(Serializable id,String operator) {
        return this.userMapper.deleteById(id) > 0;
    }
}
