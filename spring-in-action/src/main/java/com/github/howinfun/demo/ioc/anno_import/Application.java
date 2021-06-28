package com.github.howinfun.demo.ioc.anno_import;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author winfun
 * @date 2021/6/27 5:19 下午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SchoolConfiguration.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
