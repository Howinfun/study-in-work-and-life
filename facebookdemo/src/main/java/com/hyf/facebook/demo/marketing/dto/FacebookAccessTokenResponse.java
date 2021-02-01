package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * AccessToken Response
 * @author winfun
 * @date 2020/11/20 9:40 上午
 **/
@Data
public class FacebookAccessTokenResponse implements Serializable {
    private static final long serialVersionUID = 1188066307537896725L;

    /**
     * 系统访问口令
     */
    @JsonProperty("access_token")
    private String accessToken;
    /** 失败时返回 */
    private Error error;

    @Data
    static class Error{
        /** 错误信息 */
        private String message;
        /** 错误代码：200-没有权限操作、100-不合法参数、3962-scope提供的权限无效，请检查拼写、3960-创建token失败、3972-System User 的名字不能重复、190-access
         * token 已过期*/
        private Integer code;
        /** 错误类型 */
        private String type;
        /** fb trace_id */
        @JsonProperty("fbtrace_id")
        private String fbTraceId;
    }
}
