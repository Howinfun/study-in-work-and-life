package com.winfun.log.sdk.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Configuration
 * @author winfun
 * @date 2021/2/25 6:23 下午
 **/
@Configuration
@ConditionalOnProperty(prefix = "log.record",name = "enabled",havingValue = "true")
public class RestTemplateConfiguration {

    @Bean("LogRecordRestTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}