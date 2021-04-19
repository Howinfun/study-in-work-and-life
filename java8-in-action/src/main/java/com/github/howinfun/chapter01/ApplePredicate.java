package com.github.howinfun.chapter01;

import com.github.howinfun.Apple;

/**
 *
 * Apple谓词
 * @author winfun
 * @date 2021/4/2 11:07 上午
 **/
@FunctionalInterface
public interface ApplePredicate {

    /**
     * 条件过滤
     * @param apple 苹果
     * @return 返回true/false
     */
    boolean filter(Apple apple);
}
