package com.winfun.log.server.service.impl;

import com.winfun.log.sdk.entity.LogRecord;
import com.winfun.log.server.mapper.LogRecordMapper;
import com.winfun.log.server.service.LogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * LogRecord Service Impl
 * @author winfun
 * @date 2021/2/25 2:05 下午
 **/
@Slf4j
@Service
public class LogRecordServiceImpl implements LogRecordService {

    @Resource
    @Qualifier("LogRecordRestTemplate")
    private LogRecordMapper logRecordMapper;

    /***
     * 增加日志记录->异步执行，不影响主业务的执行
     * @author winfun
     * @param logRecord logRecord
     * @return {@link Integer }
     **/
    @Override
    public Integer insertLogRecord(LogRecord logRecord) {
        log.info("增加日志：{}",logRecord);
        return this.logRecordMapper.insert(logRecord);
    }

    /**
     * 根据业务名称查询操作日志列表
     * @param businessName
     * @return
     */
    @Override
    public List<LogRecord> queryLogRecord(String businessName) {
        List<LogRecord> logRecordList = this.logRecordMapper.queryByBusinessName(businessName);
        return logRecordList;
    }
}
