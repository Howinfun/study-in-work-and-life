package com.github.howinfun.demo.ioc.conditional;

import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author winfun
 * @date 2021/6/29 9:10 上午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ZooConfiguration.class);
        Stream.of(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);

    }
}
