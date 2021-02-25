package com.winfun.service.impl;

import com.winfun.entity.LogRecord;
import com.winfun.mapper.LogRecordMapper;
import com.winfun.service.LogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * LogRecord Service Impl
 * @author winfun
 * @date 2021/2/25 2:05 下午
 **/
@Slf4j
@Service
public class LogRecordServiceImpl implements LogRecordService {

    @Resource
    private LogRecordMapper logRecordMapper;

    /***
     * 增加日志记录->异步执行，不影响主业务的执行
     * @author winfun
     * @param logRecord logRecord
     * @return {@link Integer }
     **/
    @Async("AsyncTaskThreadExecutor")
    @Override
    public Integer insertLogRecord(LogRecord logRecord) {
        log.info("增加日志：{}",logRecord);
        return this.logRecordMapper.insert(logRecord);
    }
}
