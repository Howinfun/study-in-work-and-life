package com.hyf.facebook.demo.messengerPlatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Facebook Menu
 * @author winfun
 * @date 2020/11/6 4:03 下午
 **/
@Data
@Accessors(chain = true)
public class FacebookMenu implements Serializable {
    private static final long serialVersionUID = -523362231633139234L;

    /**
     * 对象数组，用于定义不同语言环境的固定菜单。系统会显示 locale 属性与用户语言一致的菜单。
     */
    private String locale;
    /**
     * 设置为 true 时，禁用 Messenger 编写工具。也就是说，用户只能通过固定菜单、回传、按钮和网页视图与您的智能助手互动。
     */
    @JsonProperty("composer_input_disabled")
    private Boolean composerInputDisabled;
    /**
     * 固定菜单的顶层菜单项组成的数组。最多可有三个菜单项。最多支持两个嵌套菜单。
     */
    @JsonProperty("call_to_actions")
    private List<FacebookMenuAction> callToActions;
}
