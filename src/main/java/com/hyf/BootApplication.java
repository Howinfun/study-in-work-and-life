package com.hyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author Howinfun
 * @desc
 * @date 2020/1/15
 */
@SpringBootApplication
@MapperScan(basePackages="com.hyf")
@EnableCaching
@EnableWebSocket
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
