package com.github.howinfun.demo.ioc.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: winfun
 * @date: 2021/6/27 5:37 下午
 **/
public class StudentImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        // 传入要注入到容器的类的全路径名，所以Bean实例的名称也是全路径名
        return new String[]{Student.class.getName()};
    }
}
