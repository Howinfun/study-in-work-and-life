package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Facebook Tab Request
 * @author winfun
 * @date 2020/11/19 11:46 上午
 **/
@Data
public class FacebookTabRequest implements Serializable {

    private static final long serialVersionUID = -5489157034976626560L;

    /** 应用ID */
    @JsonProperty("app_id")
    private String appId;
    /** tab 图标 */
    @JsonProperty("custom_image_url")
    private String customImageUrl;
    /** tab name */
    @JsonProperty("custom_name")
    private String customName;
    /** 标识该选项卡是否是尚未连接到此页的查看者的自定义登录选项卡的标志 */
    @JsonProperty("is_non_connection_landing_tab")
    private Boolean isNonConnectionLandingTab;
    /** 位置 */
    private Integer position;
    /** Tab key */
    private String tab;
    /** 选项卡跳转的网址 */
    private String link;
}
