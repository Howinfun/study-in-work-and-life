package com.winfun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * User Mapper
 * @author winfun
 * @date 2020/10/28 4:11 下午
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
