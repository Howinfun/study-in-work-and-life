package com.hyf.facebook.demo.messengerPlatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Facebook Greeting
 * @author winfun
 * @date 2020/11/6 3:28 下午
 **/
@Data
public class FacebookSetRequest implements Serializable {

    private static final long serialVersionUID = -3217113751111341590L;

    /**
     * 属性
     */
    private List<String> fields;
    /**
     * 欢迎语
     */
    private List<FacebookGreeting> greeting;
    /**
     * 固定菜单
     * 设置固定菜单，必须先设置开始按钮
     */
    @JsonProperty("persistent_menu")
    private List<FacebookMenu> persistentMenu;
    /**
     * 开始按钮
     */
    @JsonProperty("get_started")
    private FacebookGetStarted getStarted;
}
