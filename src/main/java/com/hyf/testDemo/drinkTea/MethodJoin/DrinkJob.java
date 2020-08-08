package com.hyf.testDemo.drinkTea.MethodJoin;

import lombok.SneakyThrows;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/7
 */
public class DrinkJob implements Runnable{

    private Thread cleanThread;
    private Thread heatThread;

    public DrinkJob(Thread cleanThread, Thread heatThread){
        this.cleanThread = cleanThread;
        this.heatThread = heatThread;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"：等待喝茶中。。。");
        cleanThread.join();
        heatThread.join();
        System.out.println(Thread.currentThread().getName()+"：我可以开始喝茶了！");
    }
}
