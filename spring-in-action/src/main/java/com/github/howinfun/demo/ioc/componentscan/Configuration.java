package com.github.howinfun.demo.ioc.componentscan;

import com.github.howinfun.demo.ioc.componentscan.color.Color;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 *
 * @author winfun
 * @date 2021/7/1 2:30 下午
 **/
//@ComponentScan(basePackages = "com.github.howinfun.demo.ioc.componentscan.color")
// @ComponentScans({
//         @ComponentScan(basePackages = "com.github.howinfun.demo.ioc.componentscan.color"),
//         @ComponentScan(basePackageClasses = {Configuration.class})
// })
// @ComponentScan(basePackageClasses = {Configuration.class}
//                                     // 指定类型为Color的组件不注入，包括子类
//                 ,excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = Color.class),
//                                     // 指定带@Component注解的组件不注入
//                                 @ComponentScan.Filter(type = FilterType.ANNOTATION,value = Component.class),
//                                 // 如果父类是Color，则不注入
//                                 @ComponentScan.Filter(type = FilterType.CUSTOM,value = CustomFilter.class)})
    @ComponentScan(basePackageClasses = Configuration.class,includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = Color.class)})
public class Configuration {
}
