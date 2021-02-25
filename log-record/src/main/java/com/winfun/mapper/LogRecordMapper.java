package com.winfun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.entity.LogRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * LogRecordMapper
 * @author winfun
 * @date 2020/11/3 5:20 下午
 **/
@Mapper
public interface LogRecordMapper extends BaseMapper<LogRecord> {
}
