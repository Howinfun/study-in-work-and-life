package com.hyf.facebook.demo.conversions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 参数：https://developers.facebook.com/docs/marketing-api/conversions-api/parameters
 * @author winfun
 * @date 2021/2/1 3:22 下午
 **/
@Data
public class FacebookConversionRequest implements Serializable {

    private static final long serialVersionUID = 769518124823428751L;

    private List<FacebookConversionData> data;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("partner_agent")
    private String partnerAgent;
    @JsonProperty("test_event_code")
    private String testEventCode;

}
