package com.hyf.testDemo.TestCurrentUtil;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.Semaphore;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/7
 * @company WCWC
 */
public class TestSemaphore {

    private static Semaphore semaphore = new Semaphore(3);

    static class MyRunnable implements Runnable{

        private Integer num;
        private String name;

        public MyRunnable(Integer num,String name){
            this.num = num;
            this.name = name;
        }

        @Override
        public void run() {

            try {
                // 获取信号，获取不到则死循环获取（CAS）
                semaphore.acquire();
                ThreadUtil.sleep(RandomUtil.randomInt(10)*this.num);
                System.out.println("线程"+this.name+"执行");
                // 如果不释放，信号量不会还原信号，其他线程就获取不了信号，就不能执行
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        // 同一时刻只能有3个线程一起执行
        for (int i = 0;i<=100;i++){
            new Thread(new MyRunnable(100,"线程"+i)).start();
        }
    }
}
