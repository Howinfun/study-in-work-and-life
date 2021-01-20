package com.winfun.serviceone;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.winfun.DubboServiceApplication;
import com.winfun.service.DubboServiceOne;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = DubboServiceApplication.class)
@RunWith(SpringRunner.class)
public class ServiceOneApplicationTests {

    static {
            List<DegradeRule> rules = new ArrayList<>();
            DegradeRule rule = new DegradeRule();
            rule.setResource("com.winfun.service.DubboServiceOne");
            // set threshold RT, 10 ms
            rule.setCount(10);
            rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
            rule.setTimeWindow(10);
            rules.add(rule);
            DegradeRuleManager.loadRules(rules);
    }

    @DubboReference
    private DubboServiceOne dubboServiceOne;

	@Test
    @SentinelResource
	public void contextLoads() {
        for (int i = 0; i < 100; i++) {
            dubboServiceOne.sayHello("winfun");
        }
	}

}
