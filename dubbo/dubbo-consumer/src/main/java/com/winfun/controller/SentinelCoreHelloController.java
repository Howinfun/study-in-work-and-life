package com.winfun.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.winfun.entity.pojo.ApiResult;
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
@RequestMapping("/sentinel/core")
public class SentinelCoreHelloController {

    public static final String RESOURCE_NAME = "dubboServiceOne";
    @DubboReference(check = false,lazy = true,retries = 0)
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
        // 限流规则
        final FlowRule flowRule = new FlowRule();
        flowRule.setResource(RESOURCE_NAME);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 1 QPS
        flowRule.setCount(1);
        flowRules.add(flowRule);
        // 熔断规则
        final DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(RESOURCE_NAME);
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 2个异常数
        degradeRule.setCount(1);
        // 时间窗口长度，单位为秒
        degradeRule.setTimeWindow(5);
        // 最小请求数
        degradeRule.setMinRequestAmount(5);
        // 熔断时长：当5秒内，10个请求里面出现2个异常，则进行熔断，熔断时长为10s
        degradeRule.setStatIntervalMs(10000);
        degradeRules.add(degradeRule);
        FlowRuleManager.loadRules(flowRules);
        DegradeRuleManager.loadRules(degradeRules);
    }

    @GetMapping("/hello/{name}")
    public ApiResult sayHello(@PathVariable("name") final String name){
        return this.sayHelloByDubbo2Code(name);
    }

    @GetMapping("/hi/{name}")
    public ApiResult sayHi(@PathVariable("name") final String name){
        return this.dubboServiceTwo.sayHi(name);
    }

    /***
     * 接入Sentinel方式：植入硬代码进行熔断限流
     * @author winfun
     * @param name name
     * @return {@link ApiResult<String> }
     **/
    private ApiResult<String> sayHelloByDubbo2Code(final String name) {

        ApiResult<String> result;
        Entry entry = null;
        // 务必保证 finally 会被执行
        try {
            entry = SphU.entry(RESOURCE_NAME);
            result = this.dubboServiceOne.sayHello(name);
        }  catch (BlockException e) {
            if (e instanceof DegradeException){
                log.error("资源：{} 被熔断了,message is {}",RESOURCE_NAME,e.getMessage());
                result = ApiResult.fail("hello fallback");
            }else {
                log.error("资源：{} 被流控了",RESOURCE_NAME);
                result = ApiResult.fail("hello block");
            }
        } catch (Exception e){
            log.error("资源：{} 发生异常,message is {}",RESOURCE_NAME,e.getMessage());
            // 若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(e, entry);
            result = ApiResult.fail("exception");
            //throw new RuntimeException("业务处理发生异常");
        } finally {
            // 务必保证 exit，务必保证每个 entry 与 exit 配对
            if (entry != null) {
                entry.exit();
            }
        }
        return result;
    }
}
