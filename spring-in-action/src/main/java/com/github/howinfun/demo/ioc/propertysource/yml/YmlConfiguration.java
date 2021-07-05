package com.github.howinfun.demo.ioc.propertysource.yml;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author winfun
 * @date 2021/7/5 11:34 上午
 **/
@Configuration
@ComponentScan(basePackages = {"com.github.howinfun.demo.ioc.propertysource.yml"})
@PropertySource(value = "classpath:propertySource/school.yml",encoding = "UTF-8",factory = YmlPropertySourceFactory.class)
public class YmlConfiguration {
}
