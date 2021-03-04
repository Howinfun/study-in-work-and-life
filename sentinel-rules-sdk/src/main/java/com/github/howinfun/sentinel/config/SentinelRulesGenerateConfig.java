package com.github.howinfun.sentinel.config;

import com.github.howinfun.sentinel.properties.SentinelRulesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Sentinel规则自动生成配置类
 * @author winfun
 * @date 2021/3/4 4:29 下午
 **/
@Configuration
@EnableConfigurationProperties({SentinelRulesProperties.class})
public class SentinelRulesGenerateConfig {
}
