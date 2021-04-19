package com.hyf.facebook.demo.conversions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author winfun
 * @date 2021/2/1 4:23 下午
 **/
@Data
public class FacebookConversionResponse implements Serializable {
    private static final long serialVersionUID = -2183042170827204756L;

    @JsonProperty("events_received")
    private Integer eventsReceived;

    @JsonProperty("fbtrace_id")
    private String fbTraceId;
}
