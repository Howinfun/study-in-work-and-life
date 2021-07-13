package com.github.howinfun.demo.ioc.beanPostProcessor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author winfun
 * @date 2021/7/13 9:28 上午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);

    }
}
