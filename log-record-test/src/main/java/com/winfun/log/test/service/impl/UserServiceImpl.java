package com.winfun.log.test.service.impl;

import com.winfun.log.sdk.aop.LogRecordAnno;
import com.winfun.log.sdk.entity.enums.LogRecordEnum;
import com.winfun.log.test.service.UserService;
import com.winfun.log.test.entity.User;
import com.winfun.log.test.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
            successMsg = "成功新增用户「{{#user.name}}」",
            errorMsg = "新增用户失败，错误信息：「{{#_errorMsg}}」")
    @Override
    public String insert(User user,String operator) {
        if (StringUtils.isEmpty(user.getName())){
            throw new RuntimeException("用户名不能为空");
        }
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
            operator = "#operator",
            successMsg = "成功删除用户,用户ID「{{#id}}」",
            errorMsg = "删除用户失败，错误信息：「{{#_errorMsg}}」")
    @Override
    public Boolean delete(Serializable id,String operator) {
        return this.userMapper.deleteById(id) > 0;
    }
}
