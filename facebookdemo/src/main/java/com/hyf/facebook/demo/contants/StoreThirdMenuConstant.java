package com.hyf.facebook.demo.contants;

/**
 * 菜单常量
 * @author winfun
 * @date 2020/11/9 3:07 下午
 **/
public interface StoreThirdMenuConstant {

    /** 菜单类型-纯文字 */
    Integer MENU_TYPE_TEXT = 1;
    /** 菜单类型-链接 */
    Integer MENU_TYPE_URL = 2;
    /** 菜单类型-商品 */
    Integer MENU_TYPE_PRODUCT = 3;

    /** Facebook 菜单类型-web_url */
    String FACEBOOK_MENU_TYPE_URL = "web_url";
    /** Facebook 菜单类型-postback */
    String FACEBOOK_MENU_TYPE_POSTBACK = "postback";
    /** Facebook 菜单类型-nested */
    String FACEBOOK_MENU_TYPE_NESTED = "nested";

    /** 启用状态：关闭 */
    Boolean MENU_VALID_CLOSE = false;
    /** 启用状态：启用 */
    Boolean MENU_VALID_OPEN = true;

    /** 预设菜单：是 */
    Boolean MENU_IS_DEFAULT = true;
    /** 预设菜单：否 */
    Boolean MENU_NOT_DEFAULT = false;

    /** 是否为预设菜单-是 */
    Boolean MENU_IS_DELETED = true;
    /** 是否为预设菜单-否 */
    Boolean MENU_NOT_DELETED = false;
}
