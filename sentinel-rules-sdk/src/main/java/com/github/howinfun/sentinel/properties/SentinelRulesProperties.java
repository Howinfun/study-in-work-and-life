package com.github.howinfun.sentinel.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Sentinel规则配置
 * @author winfun
 * @date 2021/3/4 4:26 下午
 **/
@ConfigurationProperties(prefix = "sentinel.rules")
public class SentinelRulesProperties {

    private Boolean enabled;
}
