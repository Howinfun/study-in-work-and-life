package com.hyf.testDemo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Howinfun
 * @desc 推送给h5的消息
 * @date 2019/10/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeMessage implements Serializable {

    /**
     * MsgConstant
     */
    private String messageType;
    private boolean success;
    private Object data;

    /**
     * 群发为true
     * 个发为false
     */
    //private boolean massMessage = false;

    //private boolean multipleCharge;



}
