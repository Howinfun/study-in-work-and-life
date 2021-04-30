package com.winfun;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

/**
 * 接入 Sentinel-Dashboard，启动项目时需要增加下面的 JVM 参数：
 * -Djava.net.preferIPv4Stack=true -Dproject.name=dubbo-consumer-demo -Dcsp.sentinel.api.port=8720  -Dcsp.sentinel
 * .dashboard.server=localhost:8080
 *
 * -Dcsp.sentinel.api.port：客户端的 port，用于上报相关信息
 * -Dcsp.sentinel.dashboard.server：控制台的地址
 * -Dproject.name：应用名称，会在控制台中显示
 * -Djava.net.preferIPv4Stack：某些环境下本地运行 Dubbo 服务还需要加上
 * @author winfun
 * @date 2021/1/22 2:02 下午
 **/
@EnableDubbo
//@EnableApolloConfig
@ServletComponentScan
@SpringBootApplication
public class DubboServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboServiceApplication.class, args);
	}

    /**
     * 需要引入一下，因为 Sentinel 提供的这个切面是没有加 @Component 注解的
     * @return
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
