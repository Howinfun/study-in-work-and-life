package com.winfun.log.sdk.service;

import com.winfun.log.sdk.entity.LogRecord;
import com.winfun.log.sdk.pojo.ApiResult;

/**
 *
 * LogRecord Service
 * @author winfun
 * @date 2021/2/25 2:04 下午
 **/
public interface LogRecordSDKService {

    /***
     * 增加日志记录
     * @author winfun
     * @param logRecord logRecord
     * @return {@link ApiResult<Integer> }
     **/
    ApiResult<Integer> insertLogRecord(LogRecord logRecord);
}
