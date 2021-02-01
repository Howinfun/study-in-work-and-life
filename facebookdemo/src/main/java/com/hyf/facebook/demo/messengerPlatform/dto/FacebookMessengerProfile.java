package com.hyf.facebook.demo.messengerPlatform.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

/**
 * @author Ethan
 * @Description
 * @Date 2020-12-09 16:41:23
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FacebookMessengerProfile {
    /**
     * {
     * "data": [
     * {
     * "whitelisted_domains": [
     * "https://facebook.com/"
     * ],
     * "greeting": [
     * {
     * "locale": "default",
     * "text": "Hello!"
     * },
     * {
     * "locale": "en_US",
     * "text": "Timeless apparel for the masses."
     * }
     * ],
     * "ice_breakers":[
     * {
     * "question": "<QUESTION>",
     * "payload": "<PAYLOAD>"
     * },
     * {
     * "question": "<QUESTION>",
     * "payload": "<PAYLOAD>"
     * }
     * ]
     * }
     * ]
     * }
     */

    private List<MessengerProfile> data;

    @Data
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class MessengerProfile {
        private List<String> whitelistedDomains;
        private List<Greeting> greeting;
        private GetStarted getStarted;
        private List<IceBreaker> iceBreakers;
        private List<PersistentMenu> persistentMenu;
        private String accountLinkingUrl;
        private Boolean subjectToNewEuPrivacyRules;


        @Data
        public static class Greeting {
            private String locale;
            private String text;
        }

        @Data

        public static class GetStarted {
            private String payload;
        }

        @Data

        public static class IceBreaker {
            private String question;
            private String payload;
        }

        @Data
        @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
        public static class PersistentMenu {
            private String locale;
            private Boolean composerInputDisabled;
            private List<MenuItem> callToActions;
            private List<String> disabledSurfaces;

            @Data
            @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
            public static class MenuItem {

                private String type;
                private String title;
                private String url;
                private String payload;
                private String webviewHeightRatio;
                private Boolean messengerExtensions;
                private String fallbackUrl;
                private String webviewShareButton;


            }
        }

    }
}

