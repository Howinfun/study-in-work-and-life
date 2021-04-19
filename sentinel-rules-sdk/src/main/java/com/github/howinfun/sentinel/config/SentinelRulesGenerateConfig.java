package com.github.howinfun.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.github.howinfun.sentinel.pojo.SentinelDegradeRule;
import com.github.howinfun.sentinel.pojo.SentinelFlowRule;
import com.github.howinfun.sentinel.properties.SentinelRulesProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Sentinel规则自动生成配置类
 * @author winfun
 * @date 2021/3/4 4:29 下午
 **/
@Configuration
@ConditionalOnProperty(prefix = "sentinel.rules", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({SentinelRulesProperties.class})
public class SentinelRulesGenerateConfig implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        SentinelRulesProperties sentinelRulesProperties =
                this.applicationContext.getBean(SentinelRulesProperties.class);
        List<SentinelFlowRule> flowRuleList = sentinelRulesProperties.getFlowRuleList();
        List<SentinelDegradeRule> degradeRuleList = sentinelRulesProperties.getDegradeRuleList();

        final List<FlowRule> flowRules = new ArrayList<>();
        final List<DegradeRule> degradeRules = new ArrayList<>();
        // 处理流控规则
        flowRuleList.forEach(sentinelFlowRule -> {
            FlowRule flowRule = new FlowRule();
            BeanUtils.copyProperties(sentinelFlowRule,flowRule);
            flowRules.add(flowRule);
        });
        // 处理熔断规则
        degradeRuleList.forEach(sentinelDegradeRule -> {
            DegradeRule degradeRule = new DegradeRule();
            BeanUtils.copyProperties(sentinelDegradeRule,degradeRule);
            degradeRules.add(degradeRule);
        });
        FlowRuleManager.loadRules(flowRules);
        DegradeRuleManager.loadRules(degradeRules);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
