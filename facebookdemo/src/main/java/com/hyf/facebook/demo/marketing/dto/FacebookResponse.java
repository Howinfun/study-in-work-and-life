package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author winfun
 * @date 2020/11/20 5:02 下午
 **/
@Data
public class FacebookResponse implements Serializable {

    private static final long serialVersionUID = -4097439184896148106L;
    /** 是否成功，成功返回true，失败直接不返回该字段 */
    private Boolean success;
    /** 失败时返回 */
    private Error error;

    @Data
    static class Error{
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
