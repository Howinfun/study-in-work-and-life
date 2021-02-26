package com.winfun.log.server.controller;

import com.winfun.log.sdk.entity.LogRecord;
import com.winfun.log.sdk.pojo.ApiResult;
import com.winfun.log.server.service.LogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/insert")
    public ApiResult<Object> insert(@RequestBody LogRecord logRecord){
        Integer count = this.logRecordService.insertLogRecord(logRecord);
        return ApiResult.success(count);
    }
}

