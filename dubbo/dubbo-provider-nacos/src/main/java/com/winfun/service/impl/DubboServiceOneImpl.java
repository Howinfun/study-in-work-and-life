package com.winfun.service.impl;

import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * DubboServiceOneImpl
 * @author winfun
 * @date 2020/10/29 5:04 下午
 **/
@DubboService(interfaceClass = DubboServiceOne.class)
public class DubboServiceOneImpl implements DubboServiceOne {
    /***
     *  say hello
     * @author winfun
     * @param name name
     * @return {@link ApiResult<String> }
     **/
    @SneakyThrows
    @Override
    public ApiResult<String> sayHello(String name) {

        return ApiResult.success("hello "+name+",version is 1.0.0");
    }
}
