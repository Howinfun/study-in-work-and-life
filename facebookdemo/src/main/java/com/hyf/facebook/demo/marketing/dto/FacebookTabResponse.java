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
public class FacebookTabResponse implements Serializable {

    private static final long serialVersionUID = 1873194904717157112L;
    /** 是否成功，成功返回true，失败直接不返回该字段 */
    private Boolean success;
    /** 失败时返回 */
    private Error error;

    @Data
    public static class Error{
        /** 错误信息 */
        private String message;
        /** 错误代码：200-没有权限操作、100-不合法参数、210：用户不可见 */
        private Integer code;
        /** 错误类型 */
        private String type;
        /** fb trace_id */
        @JsonProperty("fbtrace_id")
        private String fbTraceId;
    }
}
