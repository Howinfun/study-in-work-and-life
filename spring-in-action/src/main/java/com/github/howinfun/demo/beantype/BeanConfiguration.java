package com.github.howinfun.demo.beantype;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: winfun
 * @date: 2021/6/19 5:22 下午
 **/
@Configuration
public class BeanConfiguration {

    @Bean
    public Child child(){
        return new Child().setWantBuy("ball");
    }

    /**
     * 我们这里会注入一个Ball实例对象，bean的名字为ball
     */
    @Bean
    public Ball ball(){
        return new Ball("ball2");
    }

    /**
     * 利用工厂bean，这里也会注入一个Ball的实例对象，bean的名称为toyFactoryBean
     * 所以如果我们直接调用上下文的 <T> T getBean(Class<T> var1) 方法，会报错，因为Toy类型的Bean实例有两个。
     * 我们应该根据beanName去获取，或者调用 <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) 方法去获取集合
     *
     * FactoryBean 生产Bean的机制是延迟生产，当获取的时候才生产
     */
    @Bean
    public ToyFactoryBean toyFactoryBean(Child child){

        return new ToyFactoryBean().setChild(child);
    }
}
