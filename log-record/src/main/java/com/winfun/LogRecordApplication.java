package com.winfun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan("com.winfun")
@EnableAspectJAutoProxy
@SpringBootApplication
public class LogRecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogRecordApplication.class, args);
	}

}
