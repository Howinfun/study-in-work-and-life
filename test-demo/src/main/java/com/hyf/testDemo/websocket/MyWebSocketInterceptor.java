package com.hyf.testDemo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MyWebSocketInterceptor implements HandshakeInterceptor {

    /** token,phone */
    private static Map<String,String> tokens = new HashMap<>(20);

    @PostConstruct
    private void init(){
        tokens.put("123","15627236666");
        tokens.put("234","15627236667");
        tokens.put("345","15627236668");
        tokens.put("456","15627236669");
    }

    /**
     * 将前端传的值放至WebSocketSession的attributes
     * @param request
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            // 获取请求路径携带的参数
            String token = serverHttpRequest.getServletRequest().getParameter("Authorization");
            String iden = serverHttpRequest.getServletRequest().getParameter("iden");
            String phone = tokens.get(token);
            if (StringUtils.isNotBlank(phone)){
                log.info("用户{}连接到websocket,标识为{}!",phone,iden);
                attributes.put("phone", phone);
                attributes.put("iden",iden);
                return true;
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}