package com.hyf.facebook.demo.conversions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 参数：https://developers.facebook.com/docs/marketing-api/conversions-api/parameters
 * @author winfun
 * @date 2021/2/1 3:22 下午
 **/
@Data
public class FacebookConversionData implements Serializable {

    private static final long serialVersionUID = 459158464533612095L;
    /**
     * A Facebook pixel Standard Event or Custom Event name. This field is used to deduplicate events sent by both Facebook Pixel and Conversions API. event_id is also used in deduplication.
     * Required
     */
    @JsonProperty("event_name")
    private String eventName;
    /**
     * A Unix timestamp in seconds indicating when the actual event occurred. The specified time may be earlier than
     * the time you send the event to Facebook. This is to enable batch processing and server performance
     * optimization. You must send this date in GMT time zone.
     *
     * event_time can be up to 7 days before you send an event to Facebook. If any event_time in data is greater than
     * 7 days in the past, we return an error for the entire request and process no events.
     * Required
     */
    @JsonProperty("event_time")
    private Long eventTime;
    @JsonProperty("event_id")
    private String eventId;
    /**
     * The browser URL where the event happened.
     */
    @JsonProperty("event_source_url")
    private String eventSourceUrl;
    /**
     * Required
     */
    @JsonProperty("user_data")
    private UserData userData;
    @JsonProperty("custom_data")
    private CustomData customData;
    /**
     * A flag that indicates we should not use this event for ads delivery optimization. If set to true, we only use the event for attribution.
     */
    @JsonProperty("opt_out")
    private Boolean optOut;
    /**
     * This field allows you to specify where your conversions occurred. Knowing where your events took place helps ensure your ads go to the right people.
     */
    @JsonProperty("action_source")
    private String actionSource;

}
