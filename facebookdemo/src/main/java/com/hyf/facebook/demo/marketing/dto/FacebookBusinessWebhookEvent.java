package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author winfun
 * @date 2020/12/29 11:34 上午
 **/
@Data
public class FacebookBusinessWebhookEvent {

    private String object;
    private List<Entry> entry;

    @Data
    public static class Entry {

        private String id;
        private String uid;
        private Long time;
        private List<Change> changes;

        @Data
        public static class Change {

            private String field;
            private Value value;


            @Data
            public static class Value{
                @JsonProperty("business_id")
                private String businessId;
                /**
                 * "profiles":[
                 *      "<page_id>",
                 *       "<instagram_businesss_id>"
                 *  ]
                 */
                private List<String> profiles;
                /**
                 * <page_id>
                 */
                private List<String> pages;
                /**
                 * <instagram_businesss_id>
                 */
                @JsonProperty("instagram_profiles")
                private List<String> instagramProfiles;
                private Commerce commerce;
                @JsonProperty("install_time")
                private Long installTime;
                @JsonProperty("pixel_id")
                private String pixelId;
                @JsonProperty("access_token")
                private String accessToken;

                @Data
                public static class Commerce {

                    @JsonProperty("onsite_eligible")
                    private Boolean onsiteEligible;
                }
            }
        }

    }


}
