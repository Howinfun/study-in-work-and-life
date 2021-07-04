package com.github.howinfun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.github.howinfun.mapper")
@EnableTransactionManagement
@SpringBootApplication
public class MysqlInActionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlInActionApplication.class, args);
	}

}
