package com.github.howinfun.demo.ioc.program;

import java.util.Set;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * 扫描指定包路径
 * @author winfun
 * @date 2021/7/16 9:36 上午
 **/
public class ClassPathScanApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // ClassPathBeanDefinitionScanner 需要注入 BeanDefinitionRegistry
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(applicationContext);
        // scanner 需要增加过滤条件，否则一个都扫描不出来
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> {
            // getClassMetadata可以获取到扫描类的相关元信息，此处把Animal的子类都拿出来
            // metadataReader 包含类元信息、注解元信息，所以我们可以联想到 @Component 等系列注解可以靠这个来完成注解注入组件
            return metadataReader.getClassMetadata().getSuperClassName().equals(Animal.class.getName());
        });
        /**
         * scan方法，会根据过滤器扫描到合适的类，然后生成BeanDefinitio并注入到IOC容器中
         */
        /*int beanDefinitionCount = scanner.scan("com.github.howinfun.demo.ioc.program");
        System.out.println("共扫描"+beanDefinitionCount+"个bean定义");*/

        /**
         * 返回所有符合扫描条件的BeanDefinition，但是这些BeanDefinition没有被注入到IOC容器中
         */
        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("com.github.howinfun.demo.ioc.program");
        // 手动注入
        beanDefinitions.stream().forEach(beanDefinition -> applicationContext.registerBeanDefinition(beanDefinition.getBeanClassName(),beanDefinition));
        // 手动调用refreshed
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(Cat.class));
        System.out.println(applicationContext.getBean(Dog.class));
    }
}
