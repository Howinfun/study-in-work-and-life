package com.winfun;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboServiceTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboServiceTwoApplication.class, args);
	}

}
