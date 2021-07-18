package com.github.howinfun.demo.ioc.conditon_demo.version2;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author: winfun
 * @date: 2021/7/18 8:53 上午
 **/
@Configuration
@PropertySource("enablejdbc/jdbc.properties")
public class JdbcConfigurationV2 {

    @Autowired
    private Environment environment;

    @Bean
    @ConditionOnClassName("com.mysql.cj.jdbc.Driver")
    public DataSource mysqlDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(environment.getProperty("mysql.jdbc.url"));
        dataSource.setUsername(environment.getProperty("mysql.jdbc.username"));
        dataSource.setPassword(environment.getProperty("mysql.jdbc.password"));
        return dataSource;
    }

    @Bean
    @ConditionOnClassName("oracle.jdbc.driver.OracleDriver")
    public DataSource oracleDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl(environment.getProperty("h2.jdbc.url"));
        dataSource.setUsername(environment.getProperty("h2.jdbc.username"));
        dataSource.setPassword(environment.getProperty("h2.jdbc.password"));
        return dataSource;
    }

    @Bean
    @ConditionOnClassName("org.h2.Driver")
    public DataSource h2DataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(environment.getProperty("h2.jdbc.url"));
        dataSource.setUsername(environment.getProperty("h2.jdbc.username"));
        dataSource.setPassword(environment.getProperty("h2.jdbc.password"));
        return dataSource;
    }
}
