package com.hyf.facebook.demo.messengerPlatform.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 店铺菜单设定
 * @author winfun
 * @date 2020/11/7 10:51 上午
 **/
@Data
@Accessors(chain = true)
public class StoreThirdMenuConfig implements Serializable {

    private static final long serialVersionUID = -4994423243237050930L;

    /**
     * 菜单名称
     */
    private String menuTitle;
    /**
     * 菜单类型(1:纯文字,2:跳转链接,3:查看商品)
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;
    /**
     * 纯文字
     */
    private String menuText;
    /**
     * url
     */
    private String menuUrl;
    /**
     * payload
     */
    private String menuPayload;
}
