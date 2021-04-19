package com.winfun.log.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.log.sdk.entity.LogRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * LogRecordMapper
 * @author winfun
 * @date 2020/11/3 5:20 下午
 **/
@Mapper
public interface LogRecordMapper extends BaseMapper<LogRecord> {

    /**
     * 根据businessName查询操作日志记录列表
     * @param businessName
     * @return
     */
    @Select("select * from log_record where business_name = #{businessName} order by create_time desc")
    List<LogRecord> queryByBusinessName(String businessName);
}
