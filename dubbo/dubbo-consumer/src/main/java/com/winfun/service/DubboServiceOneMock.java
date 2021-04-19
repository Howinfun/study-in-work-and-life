package com.winfun.service;

import com.winfun.entity.pojo.ApiResult;

/**
 *
 * @author winfun
 * @date 2021/2/1 5:15 下午
 **/
public class DubboServiceOneMock implements DubboServiceOne {
    /***
     *  say hello
     * @author winfun
     * @param name name
     * @return {@link ApiResult <String> }
     **/
    @Override
    public ApiResult<String> sayHello(String name) {
        return ApiResult.fail("mock-fail");
    }
}
