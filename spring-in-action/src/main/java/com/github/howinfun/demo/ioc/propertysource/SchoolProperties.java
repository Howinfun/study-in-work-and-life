package com.github.howinfun.demo.ioc.propertysource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 学校配置类
 * @author winfun
 * @date 2021/7/5 9:56 上午
 **/
@Data
@Component
public class SchoolProperties {

    @Value("${school.name}")
    private String name;
    @Value("${school.location}")
    private String location;
    @Value("${school.level}")
    private String level;
}
