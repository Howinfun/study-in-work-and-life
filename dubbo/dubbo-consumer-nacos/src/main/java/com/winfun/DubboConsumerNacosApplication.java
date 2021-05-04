package com.winfun;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author winfun
 * @date 2021/1/22 2:02 下午
 **/
@EnableDubbo(scanBasePackages = "com.winfun")
@ServletComponentScan
@SpringBootApplication
public class DubboConsumerNacosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboConsumerNacosApplication.class, args);
	}
}
