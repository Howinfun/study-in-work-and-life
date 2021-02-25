package com.winfun.service;

import com.winfun.entity.LogRecord;

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
}
