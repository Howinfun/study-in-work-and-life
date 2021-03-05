package com.github.howinfun.sentinel.properties;

import com.github.howinfun.sentinel.pojo.SentinelDegradeRule;
import com.github.howinfun.sentinel.pojo.SentinelFlowRule;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Sentinel规则配置
 * @author winfun
 * @date 2021/3/4 4:26 下午
 **/
@Data
@ConfigurationProperties(prefix = "sentinel.rules")
public class SentinelRulesProperties {

    private Boolean enabled;
    private List<SentinelFlowRule> flowRuleList;
    private List<SentinelDegradeRule> degradeRuleList;
}
