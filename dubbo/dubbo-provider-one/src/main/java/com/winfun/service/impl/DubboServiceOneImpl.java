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
@DubboService(interfaceClass = DubboServiceOne.class,version = "1.0.0")
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
        // dubbo 接口默认超时时间为1s，我们这里直接休眠5s
        //Thread.sleep(5000);
        // 服务直接抛出异常
        //throw new Exception("Exception 来搞事了");
        return ApiResult.success("hello "+name);
    }
}
