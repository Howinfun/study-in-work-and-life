package com.hyf.testDemo.redis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 */
@Mapper
public interface BookMapper3 extends BaseMapper<Book2> {

    Book2 selectOne();
}
