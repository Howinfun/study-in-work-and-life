package com.github.howinfun.demo.ioc.beanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * demo
 * @author winfun
 * @date 2021/7/12 9:42 上午
 **/
@Component
public class DemoBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization....");
        // 即使这里返回null也不怕，spring 本身会有兜底逻辑，如果是返回null会判断是开发者返回错误了，会直接返回方法的第一个传参，即 bean 实例
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization....");
        return null;
    }
}
