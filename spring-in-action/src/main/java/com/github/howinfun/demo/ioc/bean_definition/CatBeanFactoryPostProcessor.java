package com.github.howinfun.demo.ioc.bean_definition;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

/**
 * 利用 BeanFactoryPostProcessor删除指定BeanDefinition
 * @author: winfun
 * @date: 2021/7/11 12:21 下午
 **/
@Component
public class CatBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        /*我们可以从代码中看到 ConfigurableListableBeanFactory 的唯一实现是 DefaultListableBeanFactory，而 DefaultListableBeanFactory 实现了 BeanDefinitionRegistry 接口
        所以我们可以将 configurableListableBeanFactory 直接强转为 BeanDefinitionRegistry*/
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        // 遍历IOC容器中的所有BeanDefinition，如果 beanDefinitionName 为 myCat，则干掉
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            if (beanDefinitionName.contains("Cat")){
                beanDefinitionRegistry.removeBeanDefinition(beanDefinitionName);
            }
        }
    }
}
