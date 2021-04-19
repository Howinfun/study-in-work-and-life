package com.hyf.testDemo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Howinfun
 * @desc
 * @date 2019/11/12
 */
@Configuration
public class MyWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyWebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // webSocket通道
        // 指定处理器和路径
        registry.addHandler(new MyWebSocketHandler(), "/websocket")
                // 指定自定义拦截器
                .addInterceptors(webSocketInterceptor)
                // 允许跨域
                .setAllowedOrigins("*");
        // sockJs通道

    }

    @Scheduled(cron="0 0/1 * * * ?")
    public void sendPingToAllUser(){
        MyWebSocketHandler.sendPingToAllUser();
    }

    @Scheduled(cron="0 0/5 * * * ?")
    public void checkLastPong(){
        MyWebSocketHandler.checkLastPong(10);
    }
}
