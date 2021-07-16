package com.github.howinfun.demo.ioc.program;

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
            return metadataReader.getClassMetadata().getSuperClassName().equals(Animal.class.getName());
        });
        int beanDefinitionCount = scanner.scan("com.github.howinfun.demo.ioc.program");
        System.out.println("共扫描"+beanDefinitionCount+"个bean定义");
    }
}
