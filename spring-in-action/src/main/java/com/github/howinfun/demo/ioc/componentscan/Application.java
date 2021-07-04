package com.github.howinfun.demo.ioc.componentscan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author winfun
 * @date 2021/7/1 2:30 下午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Configuration.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
