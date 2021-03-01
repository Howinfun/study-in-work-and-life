package com.winfun.log.sdk.interfaces;

import com.winfun.log.sdk.entity.LogRecord;
import com.winfun.log.sdk.pojo.ApiResult;

import java.util.List;

/**
 *
 * 操作日志API
 * @author winfun
 * @date 2021/3/1 2:55 下午
 **/
public interface LogRecordApi {

    /**
     * 新增操作日志
     * @param logRecord 操作日志
     * @return 新增记录数
     */
    ApiResult<Integer> insertLogRecord(LogRecord logRecord);

    /**
     * 根据业务名查询操作日志列表
     * @param businessName 业务名
     * @return 操作日志列表
     */
    ApiResult<List<LogRecord>> queryLogRecord(String businessName);
}
