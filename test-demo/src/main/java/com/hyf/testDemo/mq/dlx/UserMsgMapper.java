package com.hyf.testDemo.mq.dlx;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/21
 */
@Mapper
public interface UserMsgMapper {

    @Insert("insert ignore into user_msg(id,phone,msg,failCount) values(#{id},#{phone},#{msg},#{failCount})")
    int insert(UserMsg userMsg);

    @Update("update user_msg set failCount = #{failCount} where id = #{id}")
    int update(UserMsg userMsg);
}
