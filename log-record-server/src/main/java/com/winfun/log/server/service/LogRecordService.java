package com.winfun.log.server.service;

import com.winfun.log.sdk.entity.LogRecord;

import java.util.List;

/**
 *
 * LogRecord Service
 * @author winfun
 * @date 2021/2/25 2:04 下午
 **/
public interface LogRecordService {

    /***
     * 增加日志记录
     * @author winfun
     * @param logRecord logRecord
     * @return {@link Integer }
     **/
    Integer insertLogRecord(LogRecord logRecord);

    /**
     * 根据业务名称查询操作日志列表
     * @param businessName
     * @return
     */
    List<LogRecord> queryLogRecord(String businessName);
}
