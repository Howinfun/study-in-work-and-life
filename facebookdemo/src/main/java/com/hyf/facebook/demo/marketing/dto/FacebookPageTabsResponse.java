package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: winfun
 * @date: 2020/12/27 11:39 上午
 **/
@Data
public class FacebookPageTabsResponse {

    /**
     * 页签集
     */
    private List<Data> data;
    /** 失败时返回 */
    private FacebookUserInfoResponse.Error error;

    @lombok.Data
    public static class Data {
        private String id;
        private String name;
        private String link;
        @JsonProperty("is_permanent")
        private Boolean isPermanent;
        private Integer position;
        @JsonProperty("is_non_connection_landing_tab")
        private Boolean isNonConnectionLandingTab;
    }

    @lombok.Data
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
