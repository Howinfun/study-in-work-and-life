package com.github.howinfun.demo.ioc.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 动物园是否有狮子，如果有才有人看
 * @author winfun
 * @date 2021/6/29 9:21 上午
 **/
public class ExistLionCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        // 不能用 Lion.class.getName() 来表示 Lion 的Bean实例名称（通过@Import注解导入的可以），因为我们是通过@Bean注入的，方法名才是Bean实例名称，即lion
        if (conditionContext.getBeanFactory().containsBeanDefinition("lion")){
            return true;
        }
        return false;
    }
}
