package com.github.howinfun.demo.ioc.componentscan;

import com.github.howinfun.demo.ioc.componentscan.color.Color;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 *
 * @author winfun
 * @date 2021/7/1 2:30 下午
 **/
@ComponentScan(basePackages = "com.github.howinfun.demo.ioc.componentscan.color")
@ComponentScan(basePackageClasses = {Configuration.class})
// @ComponentScans({
//         @ComponentScan(basePackages = "com.github.howinfun.demo.ioc.componentscan.color"),
//         @ComponentScan(basePackageClasses = {Configuration.class})
// })
public class Configuration {
}
