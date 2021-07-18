package com.github.howinfun.demo.ioc.conditon_demo.version2;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Conditional;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(DriverClassNameCondition.class)
public @interface ConditionOnClassName {

    String value() default "";
}
