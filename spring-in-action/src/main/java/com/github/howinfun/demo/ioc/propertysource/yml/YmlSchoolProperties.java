package com.github.howinfun.demo.ioc.propertysource.yml;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * yml格式的配置类
 * @author winfun
 * @date 2021/7/5 10:49 上午
 **/
@Data
@Component
public class YmlSchoolProperties {

    @Value("${yml.school.name}")
    private String name;
    @Value("${yml.school.location}")
    private String location;
    @Value("${yml.school.level}")
    private String level;
}
