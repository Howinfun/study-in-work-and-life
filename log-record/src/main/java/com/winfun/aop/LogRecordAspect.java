package com.winfun.aop;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.entity.LogRecord;
import com.winfun.entity.enums.LogRecordEnum;
import com.winfun.mapper.LogRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * LogRecord Aspect
 * @author winfun
 * @date 2020/11/3 4:52 下午
 **/
@Slf4j
@Aspect
@Component
public class LogRecordAspect {

    @Autowired
    ApplicationContext applicationContext;
    @Resource
    private LogRecordMapper logRecordMapper;

    @Pointcut(value = "@annotation(com.winfun.aop.LogRecordAnno)")
    public void pointcut(){}

    @Around("@annotation(logRecordAnno))")
    public void around(ProceedingJoinPoint point, LogRecordAnno logRecordAnno) throws Throwable {

        Object[] args = point.getArgs();
        String mapperName = logRecordAnno.mapperName();
        BaseMapper mapper = (BaseMapper) applicationContext.getBean(mapperName);
        LogRecordEnum logRecordEnum = logRecordAnno.logType();
        LogRecord logRecord = new LogRecord();
        switch (logRecordEnum){
            case INSERT:
                logRecord.setBeforeRecord("");
                logRecord.setAfterRecord(JSON.toJSONString(args[0]));
                point.proceed();
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
            default:
                break;
        }
    }
}
