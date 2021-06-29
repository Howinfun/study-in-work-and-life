package com.github.howinfun.demo.ioc.conditional;

import java.util.Map;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author winfun
 * @date 2021/6/29 9:31 上午
 **/
public class OnBeanCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        // 获取自定义注解信息
        Map<String, Object> attributes = annotatedTypeMetadata.getAnnotationAttributes(ConditionalOnBean.class.getName());
        Class<?>[] classes = (Class<?>[]) attributes.get("value");
        if (classes.length > 0){
            for (Class<?> aClass : classes) {
                if (!conditionContext.getBeanFactory().containsBeanDefinition(aClass.getName())){
                    return false;
                }
            }
        }
        String[] beanNames = (String[]) attributes.get("beanNames");
        if (beanNames.length > 0){
            for (String beanName : beanNames) {
                if (!conditionContext.getBeanFactory().containsBeanDefinition(beanName)){
                    return false;
                }
            }
        }
        return true;
    }
}
