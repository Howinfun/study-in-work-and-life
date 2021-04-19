package com.winfun.service;

import com.winfun.entity.pojo.ApiResult;

/**
 *
 * DubboServiceTwo
 * @author winfun
 * @date 2020/10/29 5:00 下午
 **/
public interface DubboServiceTwo {

    /***
     *  say hi
     * @author winfun
     * @param name name
     * @return {@link ApiResult<String> }
     **/
    ApiResult<String> sayHi(String name);
}
