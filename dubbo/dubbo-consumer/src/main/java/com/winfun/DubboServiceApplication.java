package com.winfun;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@EnableDubboConfig
@ServletComponentScan
@SpringBootApplication
public class DubboServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboServiceApplication.class, args);
	}

    /**
     * 需要引入一下，因为 Sentile 提供的这个切面是没有加 @Component 注解的
     * @return
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

}
