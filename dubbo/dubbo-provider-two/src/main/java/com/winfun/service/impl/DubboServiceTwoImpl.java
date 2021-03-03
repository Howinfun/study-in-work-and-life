package com.winfun.service.impl;

import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceTwo;
import lombok.SneakyThrows;
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
    @SneakyThrows
    @Override
    public ApiResult<String> sayHi(String name) {
        // dubbo 接口默认超时时间为1s，我们这里直接休眠5s
        //Thread.sleep(5000);
        return ApiResult.success("hi "+name);
    }
}
