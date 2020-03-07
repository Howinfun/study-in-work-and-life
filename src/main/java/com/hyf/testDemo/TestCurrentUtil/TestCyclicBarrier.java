package com.hyf.testDemo.TestCurrentUtil;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/7
 * @company WCWC
 */
public class TestCyclicBarrier {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    static class MyRunnable implements Runnable{

        private String name;

        public MyRunnable(String name){
            this.name = name;
        }

        @Override
        public void run() {

            try {
                // 当有n个线程都调用await(),所有线程一起开始执行
                cyclicBarrier.await();
                System.out.println("线程"+this.name+"开动");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new MyRunnable("线程1")).start();
        ThreadUtil.sleep(2000);
        new Thread(new MyRunnable("线程2")).start();
        ThreadUtil.sleep(1000);
        new Thread(new MyRunnable("线程3")).start();
    }
}
