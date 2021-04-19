package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * AccessToken Request
 * @author winfun
 * @date 2020/11/20 9:40 上午
 **/
@Data
public class FacebookAccessTokenRequest implements Serializable {
    private static final long serialVersionUID = 1188066307537896725L;

    /** （必填）应用ID */
    @JsonProperty("app_id")
    private String appId;
    /** （必填）权限列表 */
    private String scope;
    @JsonProperty("access_token")
    private String accessToken;
    /** fbe_external_business_id是Facebook Business Extension客户端的可选标识符 */
    @JsonProperty("fbe_external_business_id")
    private String fbeExternalBusinessId;
    @JsonProperty("system_user_name")
    private String systemUserName;
}
