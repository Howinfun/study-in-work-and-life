package com.github.howinfun.demo.ioc.anno_import;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 教室配置类
 * 注入教室到Spring容器中
 * @author winfun
 * @date 2021/6/28 8:34 上午
 **/
@Configuration
public class ClassroomConfig {

    @Bean
    public Classroom classroom(){
        return new Classroom();
    }
}
