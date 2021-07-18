package com.github.howinfun.demo.ioc.conditon_demo.version2;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 模块装配&条件装配&配置外部化
 *  1、我们这里可根据项目是否添加对应数据库的Driver依赖，如果有则初始化对应的DataSource
 *  2、支持配置外部化，调整jdbc参数不改动代码
 * @author: winfun
 * @date: 2021/7/18 8:51 上午
 **/
@EnableJdbc
@Configuration
public class ApplicationV2 {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationV2.class);
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        DataSource mysqlDataSource = applicationContext.getBean("mysqlDataSource",DataSource.class);
        query(mysqlDataSource).forEach(System.out::println);

    }

    private static List<Object> query(DataSource dataSource) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.execute(dataSource.getConnection(),"select * from user", new BeanListHandler(Object.class));
    }
}
