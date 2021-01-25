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
import com.winfun.fallback.DefaultDubboFallback;
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
        // 1 QPS
        flowRule.setCount(1);
        flowRules.add(flowRule);
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
        // 20 QPS
        dobboInterfaceFlowRule.setCount(1);
        flowRules.add(dobboInterfaceFlowRule);
        // 熔断规则
        final DegradeRule dubboInterfaceDegradeRule = new DegradeRule();
        dubboInterfaceDegradeRule.setResource(DUBBO_INTERFACE_RESOURCE_NAME);
        dubboInterfaceDegradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 2个异常数
        dubboInterfaceDegradeRule.setCount(2);
        // 时间窗口长度，单位为秒
        dubboInterfaceDegradeRule.setTimeWindow(5);
        degradeRules.add(dubboInterfaceDegradeRule);
        FlowRuleManager.loadRules(flowRules);
        DegradeRuleManager.loadRules(degradeRules);
        DubboAdapterGlobalConfig.setConsumerFallback(new DefaultDubboFallback());
    }

    @GetMapping("/{name}")
    //@SentinelResource(value=RESOURCE_NAME,fallback = "sayHelloFallback",blockHandler = "sayHelloBlock")
    public ApiResult sayHello(@PathVariable("name") final String name){
        String hello = this.dubboServiceOne.sayHello(name);
        return ApiResult.success(hello);
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

    /**
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
     * @param name
     * @param throwable
     * @return
     */
    public ApiResult sayHelloFallback(final String name, final Throwable throwable){
        log.error("资源：{} 被熔断了,message is {}",RESOURCE_NAME,throwable.getMessage());
        return ApiResult.fail("hello fallback");
    }

    /**
     * BlockHandler 函数
     * blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException
     * @param name
     * @param exception
     * @return
     */
    public ApiResult sayHelloBlock(final String name, final BlockException exception){
        log.error("资源：{} 被流控了",RESOURCE_NAME);
        return ApiResult.fail("hello block");
    }
}
