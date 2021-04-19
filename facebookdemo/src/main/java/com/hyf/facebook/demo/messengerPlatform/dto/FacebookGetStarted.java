package com.hyf.facebook.demo.messengerPlatform.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Facebook getStarted
 * @author winfun
 * @date 2020/11/6 4:32 下午
 **/
@Data
@Accessors(chain = true)
public class FacebookGetStarted implements Serializable {
    private static final long serialVersionUID = -7767372502607055058L;
    /**
     * payload 暂时没用
     */
    private String payload;
}
