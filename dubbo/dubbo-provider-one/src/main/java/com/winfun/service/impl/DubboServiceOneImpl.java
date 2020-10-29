package com.winfun.service.impl;

import com.winfun.service.DubboServiceOne;
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
    @Override
    public String sayHello(String name) {
        return "hello "+name;
    }
}
