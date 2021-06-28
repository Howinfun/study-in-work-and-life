package com.github.howinfun.demo.ioc.anno_import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 模块配置：注入学校模块
 * @author howinfun
 * @Import 注入：
 *          1、注入普通类  ->  校长
 *          2、注入Configuration配置类  ->  课室
 *          3、利用ImportSelector注入  ->  老师
 *          4、利用ImportBeanDefinitionRegistrar注入  ->  学生
 */
@Import({Principal.class,ClassroomConfig.class, TeacherImportSelector.class, StudentImportBeanDefinitionRegistrar.class})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableSchool {
}
