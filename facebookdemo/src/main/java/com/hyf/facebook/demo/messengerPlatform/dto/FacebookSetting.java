package com.hyf.facebook.demo.messengerPlatform.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Facebook Setting
 * @author winfun
 * @date 2020/11/6 3:28 下午
 **/
@Data
public class FacebookSetting implements Serializable {

    private static final long serialVersionUID = -3677566308315998112L;

    private FacebookSetRequest data;
}
