package com.github.howinfun.demo.ioc.conditon_demo.version3;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JdbcBeanDefinitionRegistryPostProcessor.class)
@PropertySource("enablejdbc/jdbc2.properties")
public @interface EnableJdbc {
}
