package com.winfun.log.sdk.service.impl;

import com.winfun.log.sdk.entity.LogRecord;
import com.winfun.log.sdk.pojo.ApiResult;
import com.winfun.log.sdk.service.LogRecordSDKService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * LogRecord Service Impl
 * @author winfun
 * @date 2021/2/25 2:05 下午
 **/
@Slf4j
@Service
public class LogRecordSDKServiceImpl implements LogRecordSDKService {

    @Value("${log.record.url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;

    /***
     * 增加日志记录->异步执行，不影响主业务的执行
     * @author winfun
     * @param logRecord logRecord
     * @return {@link Integer }
     **/
    @Async("AsyncTaskThreadExecutor")
    @Override
    public ApiResult<Integer> insertLogRecord(LogRecord logRecord) {
        // 发起HTTP请求
        return this.restTemplate.postForObject(url+"/log/insert",logRecord,ApiResult.class);
    }

    /**
     * 查询操作日志记录列表
     * @param businessName
     * @return
     */
    @Override
    public ApiResult<List<LogRecord>> queryLogRecord(String businessName) {
        ApiResult<List<LogRecord>> result = this.restTemplate.getForObject(url+"/log/query/"+businessName,
                                                                              ApiResult.class);
        return result;
    }
}
