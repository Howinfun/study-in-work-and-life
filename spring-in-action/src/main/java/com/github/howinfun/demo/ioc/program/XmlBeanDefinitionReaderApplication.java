package com.github.howinfun.demo.ioc.program;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: winfun
 * @date: 2021/7/17 4:52 下午
 **/
public class XmlBeanDefinitionReaderApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();;
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);
        //reader.loadBeanDefinitions(new ClassPathResource("program/beans.xml"));
        reader.loadBeanDefinitions("program/beans.xml");
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(Person.class));
    }
}
