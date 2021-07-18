package com.github.howinfun.demo.ioc.conditon_demo.version1;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 模块装配 version1：
 *  1、自定义注解，使用自定义注解，可以注解装配 DataSource 和 QueryRunner，利用的是自定义注解和@Import注解
 *  2、因为@EnableJdbc需要支持各种类型的数据库，JdbcConfiguration里面会包含各种类型的数据库Bean，但是如果没加入对应数据库的依赖，在加载Driver时，会找不到对应的class
 *  3、还有一点就是，我们的配置全都是写在代码里面的，这样非常不好，所以配置也需要外部化
 *  所以在第二个版本中，我们需要做到下面两点：
 *      1、根据引入的数据库依赖，条件装配
 *      2、支持动态引入数据库配置
 * @author: winfun
 * @date: 2021/7/18 8:51 上午
 **/
@EnableJdbcV1
@Configuration
public class ApplicationV1 {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationV1.class);
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        DataSource mysqlDataSource = applicationContext.getBean("mysqlDataSource",DataSource.class);
        query(mysqlDataSource).forEach(System.out::println);
        DataSource oracleDataSource = applicationContext.getBean("oracleDataSource",DataSource.class);
        // 加载Driver时抛异常，class not found-> Class.forName()
        query(oracleDataSource).forEach(System.out::println);

    }

    private static List<Object> query(DataSource dataSource) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.execute(dataSource.getConnection(),"select * from user", new BeanListHandler(Object.class));
    }
}
