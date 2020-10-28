package com.hyf.testDemo.drinkTea.MethodJoin;

/**
 * @author Howinfun
 * @desc 用 Join 方式实现异步喝茶
 * @date 2020/8/7
 */
public class MethodJoin {

    public static void main(String[] args) {

        Thread cleanThread = new Thread(new CleanJob());
        cleanThread.setName("洗茶叶程序");

        Thread heatThread = new Thread(new HeatJob());
        heatThread.setName("烧水程序");

        Thread drinkThread = new Thread(new DrinkJob(cleanThread,heatThread));
        drinkThread.setName("喝水程序");

        drinkThread.start();
        cleanThread.start();
        heatThread.start();

    }
}
