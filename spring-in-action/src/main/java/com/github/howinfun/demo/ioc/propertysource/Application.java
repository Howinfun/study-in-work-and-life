package com.github.howinfun.demo.ioc.propertysource;

import com.github.howinfun.demo.ioc.propertysource.properties.PropertiesConfiguration;
import com.github.howinfun.demo.ioc.propertysource.properties.SchoolProperties;
import com.github.howinfun.demo.ioc.propertysource.xml.XmlConfiguration;
import com.github.howinfun.demo.ioc.propertysource.xml.XmlSchoolProperties;
import com.github.howinfun.demo.ioc.propertysource.yml.YmlConfiguration;
import com.github.howinfun.demo.ioc.propertysource.yml.YmlSchoolProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 启动类
 * @author winfun
 * @date 2021/7/5 9:50 上午
 **/
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertiesConfiguration.class, XmlConfiguration.class, YmlConfiguration.class);
        SchoolProperties schoolProperties = applicationContext.getBean(SchoolProperties.class);
        System.out.println(schoolProperties);
        XmlSchoolProperties xmlSchoolProperties = applicationContext.getBean(XmlSchoolProperties.class);
        System.out.println(xmlSchoolProperties);
        YmlSchoolProperties ymlSchoolProperties = applicationContext.getBean(YmlSchoolProperties.class);
        System.out.println(ymlSchoolProperties);
    }
}
