package com.github.howinfun.demo.ioc.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author howinfun
 * @Import 注入：
 *          1、注入普通类
 *          2、利用ImportSelector注入
 *          3、利用ImportBeanDefinitionRegistrar注入
 */
@Import({Teacher.class, StudentImportSelector.class,ClassroomImportBeanDefinitionRegistrar.class})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableSchool {
}
