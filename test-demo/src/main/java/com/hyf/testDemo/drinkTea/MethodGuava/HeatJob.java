package com.hyf.testDemo.drinkTea.MethodGuava;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/7
 */
public class HeatJob implements Callable<Boolean> {

    @Override
    public Boolean call() {
        try {
            System.out.println(Thread.currentThread().getName()+"：我要开始烧水了....");
            ThreadUtil.sleep(5, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            System.err.println(Thread.currentThread().getName()+"：烧水出现意外了！");
            return false;
        }
    }
}
