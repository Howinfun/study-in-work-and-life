package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author winfun
 * @date 2020/11/26 6:45 下午
 **/
@Data
public class FacebookPageInfoResponse {
    /**
     * 粉丝页名称
     */
    private String name;
    /**
     * 粉丝页头像信息
     */
    private FacebookPageInfoPicture picture;

    /** 失败时返回 */
    private Error error;

    @Data
    public static class FacebookPageInfoPicture{
        private FacebookPageInfoPictureData data;
    }

    @Data
    public static class FacebookPageInfoPictureData{
        private String url;
        private Integer height;
        private Integer width;
        @JsonProperty("is_silhouette")
        private Boolean isSilhouette;
    }

    @Data
    public static class Error{
        /** 错误信息 */
        private String message;
        /** 错误代码：200-没有权限操作、100-不合法参数、190-accessToken 已过期*/
        private Integer code;
        /** 错误类型 */
        private String type;
        /** fb trace_id */
        @JsonProperty("fbtrace_id")
        private String fbTraceId;
    }
}
