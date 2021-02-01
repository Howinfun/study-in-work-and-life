package com.hyf.facebook.demo.messengerPlatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Facebook Menu Action
 * @author winfun
 * @date 2020/11/6 4:07 下午
 **/
@Data
@Accessors(chain = true)
public class FacebookMenuAction implements Serializable {
    private static final long serialVersionUID = 6942732250815874837L;

    /**
     * 名称
     */
    private String title;
    /**
     * 类型：web_url、postback、nested
     */
    private String type;
    /**
     * 轻触网址按钮后将打开的网址。如果类型为 web_url，则必须提供这一属性。
     */
    private String url;
    /**
     * 将以 messaging_postbacks 事件的形式发回给 Webhook 的数据。如果类型为 postback，则必须提供这一属性。不超过 1000 个字符。
     */
    private String payload;
    /**
     * 嵌套的 menu_item，将在下一层级展开。最多可有五个菜单项。如果类型为 nested，则必须提供这一属性。一个固定菜单最多可有两个嵌套菜单。
     */
    @JsonProperty("call_to_actions")
    private List<FacebookMenuAction> callToActions;
    /**
     * 可选。网页视图的高度。有效值包括 compact、tall 和 full。
     */
    @JsonProperty("webview_height_ratio")
    private String webViewHeightRatio;
    /**
     * 可选。如果菜单项类型为 web_url 且 Messenger 功能插件 SDK 将在网页视图中使用，则该属性必须为 true。
     */
    @JsonProperty("messenger_extensions")
    private Boolean messengerExtensions;
    @JsonProperty("fallback_url")
    private String fallbackUrl;
    /**
     * 可选。设置为 hide 可禁止在网页视图分享（适用于敏感信息）。
     */
    @JsonProperty("webview_share_button")
    private String webViewShareButton;
}
