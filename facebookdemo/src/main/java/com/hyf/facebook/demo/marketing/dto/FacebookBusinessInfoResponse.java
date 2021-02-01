package com.hyf.facebook.demo.marketing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Facebook Business
 * @author winfun
 * @date 2020/11/27 4:29 下午
 **/
@Data
public class FacebookBusinessInfoResponse {

    private List<DataDetail> data;

    @Data
    public static class DataDetail{

        /**
         * Pixel 像素代码编号 (pixel_id) - 此企业的唯一 Pixel 像素代码编号，您应该使用此编号来存储和触发事件
         */
        @JsonProperty("pixel_id")
        private String pixelId;
        /**
         * 主页 (profiles) - 主页列表（Facebook 公共主页编号和/或 Instagram 商家主页编号）。这些编号可用于为其他 Facebook 集成发出独立的图谱 API 请求。如果 profiles 为空，这意味着企业已卸载 FBE。
         */
        private List<String> profiles;
        /**
         * 页面（pages）-Facebook页面ID列表。使用这些ID为您可能具有的其他Facebook Page集成创建单独的Graph API请求。如果pages不包括该字段，则表示该企业尚未将任何Facebook Pages与FBE连接。
         */
        private List<String> pages;
        /**
         * 商务管理平台编号 (business_manager_id) - 用户在 FBE 内选择的唯一商务管理平台编号，可用于将访问口令转换为代表系统用户的访问口令。
         */
        @JsonProperty("business_manager_id")
        private String businessManagerId;
        /**
         * 广告帐户编号 (ad_account_id) - 用户在 FBE 内选择的广告帐户编号。如果您的应用具有 ads_management 权限，那么您可使用此广告帐户管理广告
         */
        @JsonProperty("ad_account_id")
        private String adAccountId;
        /**
         * 目录编号 (catalog_id) - 用户在 FBE 内选择的目录编号。此编号可用于管理其产品目录。
         */
        @JsonProperty("catalog_id")
        private String catalogId;
        /**
         * 公共主页 (pages) - Facebook 公共主页编号的列表。使用这些编号为您可能具有的其他 Facebook 公共主页集成创建独立的图谱 API 请求。如果 pages 字段不在其中，这意味着企业尚未将任何 Facebook 公共主页与 FBE 相连接。
         */
        @JsonProperty("commerce_merchant_settings_id")
        private String commerceMerchantSettingsId;
    }

    @Data
    public static class DataDetailProfiles{

        /**
         * Facebook 公共主页编号
         */
        @JsonProperty("page_id")
        private String pageId;
        /**
         * Instagram 商家主页编号
         */
        @JsonProperty("instagram_business_id")
        private String instagramBusinessId;
    }

}
