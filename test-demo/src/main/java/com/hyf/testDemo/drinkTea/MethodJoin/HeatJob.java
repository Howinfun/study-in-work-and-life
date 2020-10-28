package com.hyf.testDemo.drinkTea.MethodJoin;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/7
 */
public class HeatJob implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"：我要开始烧水了....");
        ThreadUtil.sleep(5, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName()+"：我烧完水了！");
    }
}
