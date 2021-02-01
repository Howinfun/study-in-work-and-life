package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author winfun
 * 返回字段参考文档：https://developers.facebook.com/docs/marketing-api/fbe/fbe2/reference/#biz-config
 * @date 2020/11/27 6:16 下午
 **/
@Data
public class FacebookBusinessConfigResponse {


    @JsonProperty("messenger_chat")
    private FBEMessengerChatConfigData messengerChat;
    @JsonProperty("catalog_feed_scheduled")
    private FBECatalogFeedConfigData catalogFeedConfigData;

    @Data
    public static class FBEMessengerChatConfigData{
        private Boolean enabled;
        private List<String> domains;
    }

    @Data
    public static class FBECatalogFeedConfigData{
        private Boolean enabled;
        @JsonProperty("feed_url")
        private String feedUrl;
    }
}
