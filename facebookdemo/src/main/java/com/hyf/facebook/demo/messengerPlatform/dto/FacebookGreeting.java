package com.hyf.facebook.demo.messengerPlatform.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * FacebookGreeting
 * @author winfun
 * @date 2020/11/6 3:34 下午
 **/
@Data
@Accessors(chain = true)
public class FacebookGreeting implements Serializable {
    private static final long serialVersionUID = -7490595766998090699L;
    /**
     * 地区
     */
    private String locale;
    /**
     * 文案
     */
    private String text;
}
