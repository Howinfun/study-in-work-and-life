package com.winfun.service;

import com.winfun.entity.pojo.ApiResult;

/**
 *
 * DubboServiceOne
 * @author winfun
 * @date 2020/10/29 5:00 下午
 **/
public interface DubboServiceOne {

    /***
     *  say hello
     * @author winfun
     * @param name name
     * @return {@link ApiResult<String> }
     **/
    ApiResult<String> sayHello(String name);
}
