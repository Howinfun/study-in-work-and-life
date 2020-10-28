package com.hyf.testDemo.TestCurrentUtil;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.CountDownLatch;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/7
 * @company WCWC
 */
public class TestCountDownLatch {

    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    static class MyRunnable implements Runnable{

        private String name;

        public MyRunnable(String name){
            this.name = name;
        }

        @Override
        public void run() {

            ThreadUtil.sleep(RandomUtil.randomInt(10000));
            System.out.println("线程"+this.name+"执行完了");
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread(new MyRunnable("线程1")).start();
        new Thread(new MyRunnable("线程2")).start();
        new Thread(new MyRunnable("线程3")).start();
        System.out.println("主线程等待。。。");
        countDownLatch.await();
        System.out.println("主线程完成。。。");
    }

}
