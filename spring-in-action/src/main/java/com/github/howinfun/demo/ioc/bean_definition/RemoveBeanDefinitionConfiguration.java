package com.github.howinfun.demo.ioc.bean_definition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: winfun
 * @date: 2021/7/11 9:51 上午
 **/
@ComponentScan(basePackages = "com.github.howinfun.demo.ioc.bean_definition")
@Configuration
public class RemoveBeanDefinitionConfiguration {

    @Bean
    public Person myPerson(){
        return new Person().setName("winfun");
    }

    @Bean
    public Cat myCat(Person myPerson){
        return new Cat().setPerson(myPerson);
    }
}
