package com.github.howinfun.demo.ioc.conditon_demo.version3;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 模块装配&条件装配&配置外部化
 * 1、利用Spring的SPI机制&Spring提供的BeanDefinitionRegistryPostProcessor，动态判断数据库Driver的依赖引入，生成对应的BeanDefinition并注入到IOC容器中
 * 2、但是仅支持单数据源，因为配置外部化只支持单个配置
 *
 * @author: winfun
 * @date: 2021/7/18 8:51 上午
 **/
@EnableJdbc
@Configuration
public class ApplicationV3 {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationV3.class);
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        DataSource mysqlDataSource = applicationContext.getBean(DataSource.class);
        query(mysqlDataSource).forEach(System.out::println);

    }

    private static List<Object> query(DataSource dataSource) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.execute(dataSource.getConnection(),"select * from user", new BeanListHandler(Object.class));
    }
}
