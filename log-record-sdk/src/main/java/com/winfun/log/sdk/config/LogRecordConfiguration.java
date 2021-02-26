package com.winfun.log.sdk.config;

import com.winfun.log.sdk.aop.LogRecordAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LogRecord Configuration
 * @author winfun
 * @date 2021/2/25 6:23 下午
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "log.record",name = "enabled",havingValue = "true")
public class LogRecordConfiguration {

    /**
     * 将 LogRecordAspect 加入Spring 容器，让切面生效
     * @return
     */
    @Bean
    public LogRecordAspect logRecordAspect(){
        log.info("日志记录组件生效，加入切面～");
        return new LogRecordAspect();
    }
}
