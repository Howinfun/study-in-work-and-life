package com.github.howinfun.chapter01;

import com.github.howinfun.Apple;

/**
 *
 *
 * @author winfun
 * @date 2021/4/2 11:43 上午
 **/
@FunctionalInterface
public interface ApplePrint {

    /**
     * 格式化打印
     * @param apple apple
     * @return 打印字符串
     */
    String print(Apple apple);
}
