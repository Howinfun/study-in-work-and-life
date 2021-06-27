package com.github.howinfun.demo.ioc.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: winfun
 * @date: 2021/6/27 5:43 下午
 **/
public class ClassroomImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        /**
         * 参数一：bean实例名称
         * 参数二：bean定义信息
         */
        registry.registerBeanDefinition("classroom", new RootBeanDefinition(Classroom.class));
    }
}
