package com.hyf.facebook.demo.contants;

/**
 *
 * Facebook Graph Api 错误码
 * @author winfun
 * @date 2020/11/25 4:00 下午
 **/
public interface FacebookGraphApiErrorCode {

    /**
     * access token 过期
     */
    Integer ACCESS_TOKEN_EXPIRE = 190;
    /**
     * 没有权限操作
     */
    Integer PERMISSIONS_ERROR = 200;
    /**
     * 参数不合法
     */
    Integer INVALID_PARAMETER = 100;
    /**
     * 当前用户不可见
     */
    Integer USER_NOT_VISIBLE = 210;
}
