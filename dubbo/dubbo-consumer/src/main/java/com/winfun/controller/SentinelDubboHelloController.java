package com.winfun.controller;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.winfun.entity.pojo.ApiResult;
import com.winfun.fallback.DefaultDubboFallback;
import com.winfun.service.DubboServiceOne;
import com.winfun.service.DubboServiceTwo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Say Hello
 * @author winfun
 * @date 2020/10/29 5:12 下午
 **/
@Slf4j
@RestController
@RequestMapping("/sentinel/dubbo")
public class SentinelDubboHelloController {

    public static final String DUBBO_INTERFACE_RESOURCE_NAME = "com.winfun.service.DubboServiceOne";
    @DubboReference(check = false,lazy = true,retries = 0,mock = "true")
    private DubboServiceOne dubboServiceOne;
    @DubboReference(check = false,lazy = true,retries = 0)
    private DubboServiceTwo dubboServiceTwo;
    /**
     * 初始化流控规则和熔断规则
     * ps:因为我们没有接入 Sentinel Dashboard，所以得自己在代码里面设置好
     */
    static{
        // 初始化流控规则
        final List<FlowRule> flowRules = new ArrayList<>();
        final List<DegradeRule> degradeRules = new ArrayList<>();
        /**
         * 给dubbo接口设置流控，维度：接口或方法
         * 以 DubboServiceOne 接口为例子：
         * 接口：com.winfun.service.DubboServiceOne
         * 方法：com.winfun.service.DubboServiceOne:sayHello(java.lang.String)
         */
        // 限流规则
        final FlowRule dobboInterfaceFlowRule = new FlowRule();
        dobboInterfaceFlowRule.setResource(DUBBO_INTERFACE_RESOURCE_NAME);
        dobboInterfaceFlowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 1 QPS
        dobboInterfaceFlowRule.setCount(1);
        flowRules.add(dobboInterfaceFlowRule);
        // 熔断规则
        final DegradeRule dubboInterfaceDegradeRule = new DegradeRule();
        dubboInterfaceDegradeRule.setResource(DUBBO_INTERFACE_RESOURCE_NAME);
        dubboInterfaceDegradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 2个异常数
        dubboInterfaceDegradeRule.setCount(1);
        // 时间窗口长度，单位为秒
        dubboInterfaceDegradeRule.setTimeWindow(5);
        // 最小请求数
        dubboInterfaceDegradeRule.setMinRequestAmount(5);
        // 熔断时长：当5秒内，10个请求里面出现2个异常，则进行熔断，熔断时长为10s
        dubboInterfaceDegradeRule.setStatIntervalMs(10000);
        degradeRules.add(dubboInterfaceDegradeRule);
        FlowRuleManager.loadRules(flowRules);
        DegradeRuleManager.loadRules(degradeRules);
        // 全局 Fallback 函数
        DubboAdapterGlobalConfig.setConsumerFallback(new DefaultDubboFallback());
    }

    @GetMapping("/hello/{name}")
    public ApiResult sayHello(@PathVariable("name") final String name){
        return this.dubboServiceOne.sayHello(name);
    }

    @GetMapping("/hi/{name}")
    public ApiResult sayHi(@PathVariable("name") final String name){
        return this.dubboServiceTwo.sayHi(name);
    }
}
