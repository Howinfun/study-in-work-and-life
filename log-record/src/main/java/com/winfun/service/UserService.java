package com.winfun.service;

import com.winfun.entity.User;

import java.io.Serializable;

/**
 *
 * User Service
 * @author winfun
 * @date 2021/2/25 3:54 下午
 **/
public interface UserService {

    /**
     * 新增用户记录
     * @param user 用户记录
     * @param operator 操作者
     * @return 主键
     */
    String insert(User user,String operator);

    /**
     * 更新用户记录
     * @param user 用户记录
     * @param operator 操作者
     * @return 是否成功
     */
    Boolean update(User user,String operator);

    /**
     * 删除用户记录
     * @param id 主键
     * @param operator 操作者
     * @return 是否成功
     */
    Boolean delete(Serializable id,String operator);
}
