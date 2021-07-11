package com.github.howinfun.demo.ioc.bean_definition;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: winfun
 * @date: 2021/7/11 9:51 上午
 **/
@Configuration
@Import(CatImportBeanDefinitionRegistrar.class)
public class AddBeanConfiguration {
}
