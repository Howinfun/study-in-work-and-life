package com.github.howinfun.demo.ioc.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 动物园配置类
 * @author winfun
 * @date 2021/6/29 9:16 上午
 **/
// @Import 导入，Bean实例名称为全路径名
@Import(Zoo.class)
@Configuration
public class ZooConfiguration {

    @Bean
    @ConditionalOnBean(value = Zoo.class)
    public Lion lion(){
        return new Lion();
    }

    @Bean
    @ConditionalOnBean(beanNames = "lion")
    public People people(){
        return new People();
    }
}
