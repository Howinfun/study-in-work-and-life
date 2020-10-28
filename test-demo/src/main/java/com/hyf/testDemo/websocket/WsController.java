package com.hyf.testDemo.websocket;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Howinfun
 * @desc Websocket Controller
 * @date 2019/12/2
 */
@RestController
@RequestMapping("/ws")
public class WsController {

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody WsQuery query){
        NoticeMessage message = new NoticeMessage();
        message.setMessageType(MsgConstant.MSG);
        message.setData(query.getMessage());
        message.setSuccess(true);
        String msgJson = JSON.toJSONString(message);
        MyWebSocketHandler.sendMessage(query.getUser(),msgJson);
    }

    @PostMapping("/sendMessageByIden")
    public void sendMessageByIden(@RequestBody WsQuery query){
        NoticeMessage message = new NoticeMessage();
        message.setMessageType(MsgConstant.MSG);
        message.setData(query.getMessage());
        message.setSuccess(true);
        String msgJson = JSON.toJSONString(message);
        MyWebSocketHandler.sendMessageByIden(query.getUser(),query.getIden(),msgJson);
    }
}
