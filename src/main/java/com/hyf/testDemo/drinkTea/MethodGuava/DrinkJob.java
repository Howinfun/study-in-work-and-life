package com.hyf.testDemo.drinkTea.MethodGuava;

import lombok.SneakyThrows;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/7
 */
public class DrinkJob implements Runnable{

    public Boolean cleanOk = false;
    public Boolean heatOk = false;

    @SneakyThrows
    @Override
    public void run() {

        while (true){
            System.out.println(Thread.currentThread().getName()+"：我读书。。。。。");
            Thread.sleep(10000);
            if (cleanOk&&heatOk){
                System.out.println(Thread.currentThread().getName()+"：我可以开始喝茶了！");
            }
        }

    }
}
