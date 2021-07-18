package com.github.howinfun.demo.ioc.conditon_demo.version2;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.util.StringUtils;

/**
 * @author: winfun
 * @date: 2021/7/18 10:27 上午
 **/
public class DriverClassNameCondition implements Condition {
    /**
     * Determine if the condition matches.
     *
     * @param context  the condition context
     * @param metadata the metadata of the {@link AnnotationMetadata class}
     *                 or {@link MethodMetadata method} being checked
     * @return {@code true} if the condition matches and the component can be registered,
     * or {@code false} to veto the annotated component's registration
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // metadata包含类的所有注解信息，我们这里需要获取ConditionOnClassName注解的value属性值
        String className = (String) metadata.getAnnotationAttributes(ConditionOnClassName.class.getName()).getOrDefault("value","");
        if (!StringUtils.isEmpty(className)){
            try {
                Class.forName(className);
                return true;
            } catch (ClassNotFoundException e) {
                System.out.println("找不多对应的Driver");
                return false;
            }
        }
        return false;
    }
}
