package com.hyf.testDemo.redis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/24
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> queryUserList();

    @Select("select * from user where id = #{id}")
    User queryUserById(@Param("id") Long id);

    @Insert("insert into user(name,age,phone) values(#{name},#{age},#{phone})")
    Integer addUser(User user);

    @Delete("delete from user where id = #{id}")
    Integer delUser(@Param("id") Long id);

    @Update("update user set name=#{name}, age=#{age}, phone=#{phone} where id = #{id}")
    Integer updateUser(User user);
}
