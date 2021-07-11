package com.github.howinfun.demo.ioc.bean_definition;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: winfun
 * @date: 2021/7/11 9:58 上午
 **/
public class CatImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition("myPerson",new RootBeanDefinition(Person.class));
        registry.registerBeanDefinition("myCat", BeanDefinitionBuilder.genericBeanDefinition(Cat.class).addPropertyReference("person","myPerson").getBeanDefinition());
    }
}
