package com.winfun.service;

import com.winfun.entity.pojo.ApiResult;

/**
 * DubboServiceTwo 降级实现类
 * @author winfun
 * @date 2021/1/26 9:21 上午
 **/
public class DubboServiceTwoMock implements DubboServiceTwo{

    /***
     *  say hi
     * @author winfun
     * @param name name
     * @return {@link ApiResult <String> }
     **/
    @Override
    public ApiResult<String> sayHi(String name) {
        return ApiResult.fail("Mock实现类-熔断降级了");
    }
}
