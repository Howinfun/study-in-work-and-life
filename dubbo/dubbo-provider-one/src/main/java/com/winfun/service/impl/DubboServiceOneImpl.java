package com.winfun.service.impl;

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
     * @return {@link String }
     **/
    @SneakyThrows
    @Override
    public String sayHello(String name) {
        // dubbo 接口默认超时时间为1s，我们这里直接休眠5s
        return "hello "+name;
    }
}
