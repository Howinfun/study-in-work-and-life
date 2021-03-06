package com.github.howinfun.demo.ioc.propertysource.xml;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置类
 * @author winfun
 * @date 2021/7/5 9:52 上午
 **/
@Configuration
@ComponentScan(basePackages = {"com.github.howinfun.demo.ioc.propertysource.xml"})
@PropertySource(value = "classpath:propertySource/school.xml",encoding = "UTF-8")
public class XmlConfiguration {
}
