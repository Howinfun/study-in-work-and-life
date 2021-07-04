package com.github.howinfun.demo.ioc.componentscan;

import com.github.howinfun.demo.ioc.componentscan.color.Color;
import java.awt.*;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 自定义组件扫描过滤器
 * 如果父类是Color，则返回true
 * @author winfun
 * @date 2021/7/1 3:09 下午
 **/
public class CustomFilter implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        /**
         * metadataReader ：the metadata reader for the target class
         *      通过这个 Reader ，可以读取到正在扫描的类的信息（包括类的信息、类上标注的注解等）
         * metadataReaderFactory ：a factory for obtaining metadata readers for other classes (such as superclasses and interfaces)
         *      借助这个 Factory ，可以获取到其他类的 Reader ，进而获取到那些类的信息
         *      可以这样理解：借助 ReaderFactory 可以获取到 Reader ，借助 Reader 可以获取到指定类的信息
         */
        // 获取类元数据
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取类注解元数据
        AnnotationMetadata aNnotationMetadata = metadataReader.getAnnotationMetadata();
        if (classMetadata.getSuperClassName().equals(Color.class)){
            return true;
        }
        return false;
    }
}
