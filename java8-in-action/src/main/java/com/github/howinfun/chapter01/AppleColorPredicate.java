package com.github.howinfun.chapter01;

import com.github.howinfun.Apple;

/**
 *
 * @author winfun
 * @date 2021/4/2 11:17 上午
 **/
public class AppleColorPredicate implements ApplePredicate{
    /**
     * 条件过滤
     * @param apple 苹果
     * @return 返回true/false
     */
    @Override
    public boolean filter(Apple apple) {
        return "green".equals(apple.getColor());
    }
}
