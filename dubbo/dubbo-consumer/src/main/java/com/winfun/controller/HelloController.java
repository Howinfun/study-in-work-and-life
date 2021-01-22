package com.winfun.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
import com.winfun.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Say Hello
 * @author winfun
 * @date 2020/10/29 5:12 下午
 **/
@Slf4j
@RequestMapping("/hello")
@RestController
public class HelloController {

    public static final String RESOURCE_NAME = "dubboServiceOne";
    public static final String DUBBO_INTERFACE_RESOURCE_NAME = "com.winfun.service.DubboServiceOne";
    public static final String DUBBO_INTERFACE_METHOD_RESOURCE_NAME = "com.winfun.service.DubboServiceOne:sayHello(java.lang.String)";
    @DubboReference(check = false,lazy = true,retries = 1)
    private DubboServiceOne dubboServiceOne;
    @Resource
    private HelloService helloService;

    /**
     * 初始化流控规则和熔断规则
     * ps:因为我们没有接入 Sentinel Dashboard，所以得自己在代码里面设置好
     */
    static{
        // 初始化流控规则
        final List<FlowRule> flowRules = new ArrayList<>();
        // 限流规则
        final FlowRule flowRule = new FlowRule();
        flowRule.setResource(RESOURCE_NAME);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 20 QPS
        flowRule.setCount(20);
        flowRules.add(flowRule);
        FlowRuleManager.loadRules(flowRules);
        // 熔断规则
        final List<DegradeRule> degradeRules = new ArrayList<>();
        final DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(RESOURCE_NAME);
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 2个异常数
        degradeRule.setCount(2);
        // 时间窗口长度，单位为秒
        degradeRule.setTimeWindow(5);
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);

        /**
         * 给dubbo接口设置流控，维度：接口或方法
         * 以 DubboServiceOne 接口为例子：
         * 接口：com.winfun.service.DubboServiceOne
         * 方法：com.winfun.service.DubboServiceOne:sayHello(java.lang.String)
         */
        // 初始化流控规则
        final List<FlowRule> dubboFlowRules = new ArrayList<>();
        // 限流规则
        final FlowRule dobboInterfaceFlowRule = new FlowRule();
        dobboInterfaceFlowRule.setResource(DUBBO_INTERFACE_RESOURCE_NAME);
        dobboInterfaceFlowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 20 QPS
        dobboInterfaceFlowRule.setCount(20);
        dubboFlowRules.add(dobboInterfaceFlowRule);
        FlowRuleManager.loadRules(dubboFlowRules);
        // 熔断规则
        final List<DegradeRule> dubboDegradeRules = new ArrayList<>();
        final DegradeRule dubboInterfaceDegradeRule = new DegradeRule();
        dubboInterfaceDegradeRule.setResource(DUBBO_INTERFACE_RESOURCE_NAME);
        dubboInterfaceDegradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 2个异常数
        dubboInterfaceDegradeRule.setCount(2);
        // 时间窗口长度，单位为秒
        dubboInterfaceDegradeRule.setTimeWindow(5);
        dubboDegradeRules.add(dubboInterfaceDegradeRule);
        DegradeRuleManager.loadRules(dubboDegradeRules);

        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) -> {
                    throw new RuntimeException("fallback");
                });
    }

    @GetMapping("/{name}")
    public ApiResult sayHello(@PathVariable("name") final String name){
        String hello = this.dubboServiceOne.sayHello(name);
        //final String hello = this.sayHelloByDubbo2Code(name);
        //final String hello = this.helloService.sayHello(name);
        return ApiResult.success(hello);
    }


    /***
     *
     * @author winfun
     * @param name name
     * @return {@link String }
     **/
    public String sayHelloByDubbo(final String name){
        return this.dubboServiceOne.sayHello(name);
    }

    /***
     * 接入Sentinel方式：植入硬代码进行熔断限流
     * @author winfun
     * @param name name
     * @return {@link String }
     **/
    private String sayHelloByDubbo2Code(final String name) {

        String hello;
        try (Entry entry = SphU.entry(RESOURCE_NAME)){
            hello = this.dubboServiceOne.sayHello(name);
        }  catch (final BlockException e) {
            log.error("资源：{} 被流控了",RESOURCE_NAME);
            hello = "block";
        } catch (final Exception e){
            log.error("资源：{} 被熔断了,message is {}",RESOURCE_NAME,e.getMessage());
            hello = "fallback";
        }
        return hello;
    }
}
