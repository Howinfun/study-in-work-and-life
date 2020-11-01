package com.winfun;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDubboConfig
@ServletComponentScan
@SpringBootApplication
public class DubboServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboServiceApplication.class, args);
	}

}
