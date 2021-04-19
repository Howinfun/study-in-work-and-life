package com.winfun.log.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.winfun.log.test.mapper")
@SpringBootApplication(scanBasePackages = "com.winfun")
public class LogRecordTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogRecordTestApplication.class, args);
	}

}
