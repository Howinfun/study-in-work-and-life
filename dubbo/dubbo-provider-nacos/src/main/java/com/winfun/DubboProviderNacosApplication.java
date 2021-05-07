package com.winfun;

import com.winfun.service.DubboServiceOne;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScanRegistrarV2;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;

@Slf4j
@EnableDubbo(scanBasePackages = "com.winfun")
@SpringBootApplication(scanBasePackages = "com.winfun")
//@Import(DubboComponentScanRegistrarV2.class)
public class DubboProviderNacosApplication implements CommandLineRunner, ApplicationContextAware {

	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(DubboProviderNacosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String,DubboServiceOne> map  = applicationContext.getBeansOfType(DubboServiceOne.class);
		map.entrySet().forEach(entry -> {
			log.info("key is {},the result is {}",entry.getKey(),entry.getValue().sayHello("winfun"));
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
