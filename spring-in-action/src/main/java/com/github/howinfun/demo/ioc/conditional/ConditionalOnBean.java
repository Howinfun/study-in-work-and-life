package com.github.howinfun.demo.ioc.conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

/**
 * 自定义Conditional注解，通用支持组件依赖判断
 * @author winfun
 * @date 2021/6/29 9:21 上午
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(OnBeanCondition.class)
public @interface ConditionalOnBean {

    Class<?>[] value() default {};
    String[] beanNames() default {};
}
