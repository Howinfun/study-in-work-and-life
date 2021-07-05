package com.github.howinfun.demo.ioc.propertysource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 启动类
 * @author winfun
 * @date 2021/7/5 9:50 上午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertiesConfiguration.class,XmlConfiguration.class);
        SchoolProperties schoolProperties = applicationContext.getBean(SchoolProperties.class);
        System.out.println(schoolProperties);
        XmlSchoolProperties xmlSchoolProperties = applicationContext.getBean(XmlSchoolProperties.class);
        System.out.println(xmlSchoolProperties);
    }
}
