package com.github.howinfun.sentinel.listener;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.github.howinfun.sentinel.pojo.SentinelDegradeRule;
import com.github.howinfun.sentinel.pojo.SentinelFlowRule;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象监听器
 * @author winfun
 * @date 2021/3/10 11:21 上午
 **/
public abstract class AbstractSentinelRulesListener {

    public void reloadSentinelRules(List<SentinelFlowRule> flowRuleList,List<SentinelDegradeRule> degradeRuleList){

        final List<FlowRule> flowRules = new ArrayList<>();
        final List<DegradeRule> degradeRules = new ArrayList<>();
        // 处理流控规则
        flowRuleList.forEach(sentinelFlowRule -> {
            FlowRule flowRule = new FlowRule();
            BeanUtils.copyProperties(sentinelFlowRule, flowRule);
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
}
