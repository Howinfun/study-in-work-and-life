package com.winfun.log.server.controller;

import com.winfun.log.sdk.entity.LogRecord;
import com.winfun.log.sdk.pojo.ApiResult;
import com.winfun.log.server.service.LogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * LogRecord Controller
 * @author winfun
 * @date 2021/2/26 2:53 下午
 **/
@RestController
@RequestMapping("/log")
public class LogRecordController {

    @Autowired
    private LogRecordService logRecordService;

    /**
     * 插入操作日志记录
     * @param logRecord
     * @return
     */
    @PostMapping("/insert")
    public ApiResult<Object> insert(@RequestBody LogRecord logRecord){
        Integer count = this.logRecordService.insertLogRecord(logRecord);
        return ApiResult.success(count);
    }

    /**
     * 根据业务名称查询操作日志记录列表
     * @param businessName
     * @return
     */
    @GetMapping("/query/{businessName}")
    public ApiResult<List<LogRecord>> queryLogRecord(@PathVariable("businessName") String businessName){
        return ApiResult.success(this.logRecordService.queryLogRecord(businessName));
    }
}

