package com.github.howinfun.demo.ioc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author winfun
 * @date 2021/6/27 5:02 下午
 **/
@EnableSchool
@Configuration
public class SchoolConfiguration {

    @Bean
    public School school(){
        return new School();
    }
}
