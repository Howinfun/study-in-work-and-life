package com.github.howinfun.demo.beantype;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: winfun
 * @date: 2021/6/19 5:24 下午
 **/
public class BeanTypeApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        Toy toy = applicationContext.getBean("ball",Toy.class);
        Toy toy2 = applicationContext.getBean("toyFactoryBean",Toy.class);
        // 获取工厂bean，bean name 前面加 & 符号 || 根据类型获取
        ToyFactoryBean toyFactoryBean = applicationContext.getBean("&toyFactoryBean",ToyFactoryBean.class);
        ToyFactoryBean toyFactoryBean1 = applicationContext.getBean(ToyFactoryBean.class);
        System.out.println(toy);
        System.out.println(toy2);
        System.out.println(toyFactoryBean);
        System.out.println(toyFactoryBean1);
    }
}
