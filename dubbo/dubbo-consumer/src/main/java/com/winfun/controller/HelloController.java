package com.winfun.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
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
    @DubboReference(check = false,lazy = true,retries = 1)
    private DubboServiceOne dubboServiceOne;
    @Resource
    private HelloService helloService;

    /**
     * 初始化流控规则和熔断规则
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
    }

    @GetMapping("/{name}")
    public ApiResult sayHello(@PathVariable("name") final String name){
        //String hello = this.sayHelloByDubbo(name);
        //final String hello = this.sayHelloByDubbo2Code(name);
        final String hello = this.helloService.sayHello(name);
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
