package com.winfun.log.sdk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务执行线程池配置
 * @Author: winfun
 * @Date: 2020/9/21 5:38 下午
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "log.record",name = "enabled",havingValue = "true")
public class AsyncTaskThreadExecutorConfig {

    /** 核心线程数 */
    private int corePoolSize = 1;
    /** 最大线程数 */
    private int maxPoolSize = 5;
    /** 队列数 */
    private int queueCapacity = 100;


    @Bean("AsyncTaskThreadExecutor")
    public ExecutorService asyncTaskThreadExecutor(){
        log.info("start build threadPoolTaskExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("calc-thread");

        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }
}
