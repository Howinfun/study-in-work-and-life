package com.winfun.log.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.log.sdk.entity.LogRecord;
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
