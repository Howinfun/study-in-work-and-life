package com.winfun.service.impl;

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
     * @return {@link String }
     **/
    @Override
    public String sayHi(String name) {
        return "hi "+name;
    }
}
