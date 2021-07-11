package com.github.howinfun.demo.ioc.bean_definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: winfun
 * @date: 2021/7/11 9:51 上午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName);
            if (beanName.contains("Cat")){
                Cat cat = (Cat) applicationContext.getBean(beanName);
                System.out.println(cat);
            }
            if (beanName.contains("Person")){
                Person person = (Person) applicationContext.getBean(beanName);
                System.out.println(person);
            }
        }
    }
}
