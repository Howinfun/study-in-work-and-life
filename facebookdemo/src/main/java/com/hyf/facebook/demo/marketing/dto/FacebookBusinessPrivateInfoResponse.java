package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author winfun
 * @date 2020/12/29 9:02 上午
 **/
@Data
public class FacebookBusinessPrivateInfoResponse {

    private String id;
    private String name;
    @JsonProperty("profile_picture_uri")
    private String profilePictureUri;
}
