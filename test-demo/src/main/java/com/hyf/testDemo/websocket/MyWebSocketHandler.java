package com.hyf.testDemo.websocket;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2019/11/12
 */
@Slf4j
public class MyWebSocketHandler extends AbstractWebSocketHandler {

    /** <手机号码,<标识,websocketSession>> */
    private static Map<String,Map<String, WebSocketSession>> sessionMapBase = new HashMap<>(10);
    /** 记录客户端上一次返回 PONG 消息的时间：key 为 phone+iden value为时间 */
    private static Map<String, LocalDateTime> lastPong = new HashMap<>(30);

    /**
     * 创建连接后
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 获取参数
        String phone = String.valueOf(session.getAttributes().get("phone"));
        String iden = String.valueOf(session.getAttributes().get("iden"));
        Map<String,WebSocketSession> sessionMap = sessionMapBase.get(phone);
        if (sessionMap == null || sessionMap.isEmpty()){
            sessionMap = new HashMap<>(10);
            sessionMap.put(iden,session);
            sessionMapBase.put(phone,sessionMap);
        }else {
            sessionMap.put(iden,session);
        }
        log.info("websocket连接集合：{}",sessionMapBase);
    }

    /**
     * 接收消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (message instanceof TextMessage) {
            String msgJson = message.toString();
            NoticeMessage noticeMessage = JSON.parseObject(msgJson,NoticeMessage.class);
            // 心跳包处理
            if (MsgConstant.PONG.equals(noticeMessage)){
                String phone = String.valueOf(session.getAttributes().get("phone"));
                String iden = String.valueOf(session.getAttributes().get("iden"));
                String key = phone+iden;
                lastPong.put(key,LocalDateTime.now());
            }
        } else if (message instanceof BinaryMessage) {

        } else if (message instanceof PongMessage) {

        } else {
            log.error("不支持此消息类型");
        }
    }

    /**
     * 连接出错
     * @param session
     * @param exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        delClient(session);
        log.error("websockt连接发生错误:"+exception.getMessage());
    }

    /**
     * 连接关闭
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        delClient(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 后端发送消息(推送给指定用户的所有连接)
     * @param user
     * @param message
     */
    public static void sendMessage(String user, String message){
        if (sessionMapBase != null && !sessionMapBase.isEmpty()){
            sessionMapBase.forEach((key,value)->{
                WebSocketSession session = value.get(user);
                if (session != null){
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        log.error("给用户【"+user+"】推送消息失败");
                    }
                }
            });
        }
    }

    /**
     * 后端发送消息(推送给指定标识的指定用户的所有连接)
     * @param user
     * @param message
     */
    public static void sendMessageByIden(String user,String iden, String message){
        if (sessionMapBase != null && !sessionMapBase.isEmpty()){
            Map<String, WebSocketSession> sessionMap = sessionMapBase.get(iden);
            if (sessionMap != null && !sessionMap.isEmpty()){
                WebSocketSession session = sessionMap.get(user);
                if (session != null){
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        log.error("给用户【"+user+"】推送消息失败");
                    }
                }
            }
        }
    }

    /**
     * 发送心跳给所有用户
     */
    public static void sendPingToAllUser(){
        NoticeMessage noticeMessage = new NoticeMessage();
        noticeMessage.setSuccess(true);
        noticeMessage.setMessageType(MsgConstant.PING);
        noticeMessage.setData("我来给你发送PING消息了！");
        // websocket
        String jsonData = JSON.toJSONString(noticeMessage);
        if (sessionMapBase != null && !sessionMapBase.isEmpty()){
            sessionMapBase.forEach((user,value)->{
                value.forEach((iden,session)->{
                    if (session != null){
                        try {
                            session.sendMessage(new TextMessage(jsonData));
                        } catch (IOException e) {
                            log.error("给用户【"+user+"】推送消息失败");
                        }
                    }
                });
            });
        }
    }

    /**
     * 定时检查客户端返回 PONG 消息的时间，超过一定时长，将客户端删掉
     */
    public static void checkLastPong(int interval){
        if (sessionMapBase != null && !sessionMapBase.isEmpty()){
            sessionMapBase.forEach((user,value)->{
                value.forEach((iden,session)->{
                    if (session != null){
                        String key = user+iden;
                        LocalDateTime lastTime = lastPong.get(key);
                        // 如果超过十分钟没返回PONG消息，删除客户端
                        if (lastTime.isBefore(LocalDateTime.now().minusMinutes(interval))){
                            delClient(session);
                        }
                    }
                });
            });
        }
    }

    private static void delClient(WebSocketSession session){
        String clientId = String.valueOf(session.getAttributes().get("clientId"));
        String iden = String.valueOf(session.getAttributes().get("iden"));
        Map<String,WebSocketSession> sessionMap = sessionMapBase.get(iden);
        if (sessionMap != null && !sessionMap.isEmpty()){
            sessionMap.remove(clientId);
        }
    }
}
