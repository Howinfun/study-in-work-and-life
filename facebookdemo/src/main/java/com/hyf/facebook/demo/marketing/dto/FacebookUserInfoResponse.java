package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: winfun
 * @date: 2020/12/27 10:53 上午
 **/
@Data
public class FacebookUserInfoResponse {

    private String id;
    private String name;
    /** 失败时返回 */
    private Error error;

    @Data
    public static class Error{
        /** 错误信息 */
        private String message;
        /** 错误代码 */
        private Integer code;
        /** 错误类型 */
        private String type;
        /** fb trace_id */
        @JsonProperty("fbtrace_id")
        private String fbTraceId;
    }
}
