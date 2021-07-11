package com.github.howinfun.demo.ioc.bean_definition;

import java.util.Arrays;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: winfun
 * @date: 2021/7/11 9:51 上午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanNames).forEach(System.out::println);
        Cat cat = applicationContext.getBean(Cat.class);
        System.out.println(cat);
    }
}
