package com.winfun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.aop.LogRecordAnno;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 *
 * LogRecordMapper
 * @author winfun
 * @date 2020/11/3 5:20 下午
 **/
@Mapper
@Component("LogRecordMapper")
public interface LogRecordMapper extends BaseMapper<LogRecordAnno> {
}
