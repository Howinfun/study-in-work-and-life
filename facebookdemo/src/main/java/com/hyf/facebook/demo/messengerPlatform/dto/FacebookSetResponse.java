package com.hyf.facebook.demo.messengerPlatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Facebook Messenger API Response
 * @author winfun
 * @date 2020/11/6 3:28 下午
 **/
@Data
@Accessors(chain = true)
public class FacebookSetResponse implements Serializable {

    private static final long serialVersionUID = -3217113751111341590L;

    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 调用错误时返回的错误信息
     */
    private Error error;

    @Data
    public static class Error{
        /** 错误信息 */
        private String message;
        /** 错误代码：200-没有权限操作、100-不合法参数、190-access token 已过期*/
        private Integer code;
        /** 错误类型 */
        private String type;
        /** fb trace_id */
        @JsonProperty("fbtrace_id")
        private String fbTraceId;
    }
}
