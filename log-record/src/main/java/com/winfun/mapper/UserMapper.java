package com.winfun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 *
 * User Mapper
 * @author winfun
 * @date 2020/10/28 4:11 下午
 **/
@Mapper
@Component("UserMapper")
public interface UserMapper extends BaseMapper<User> {
}
