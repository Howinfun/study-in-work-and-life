package com.winfun.service.impl;

import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceTwo;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * DubboServiceTwoImpl
 * @author winfun
 * @date 2020/10/29 5:04 下午
 **/
@DubboService(interfaceClass = DubboServiceTwo.class)
public class DubboServiceTwoImpl implements DubboServiceTwo {

    /***
     *  say hi
     * @author winfun
     * @param name name
     * @return {@link ApiResult<String> }
     **/
    @Override
    public ApiResult<String> sayHi(String name) {
        return ApiResult.success("hi "+name);
    }
}
