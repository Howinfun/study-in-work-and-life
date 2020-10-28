package com.hyf.testDemo.thread;

import cn.hutool.core.thread.ThreadUtil;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/20
 */
public class ThreadSleep {

    private final static Object lock = new Object();

    public static void main(String[] args) {
        /**
         * sleep 方法
         */
        /*Thread t = new Thread(()->{
            while (true){
                ThreadUtil.sleep(10000);
            }
        });
        t.start();
        ThreadUtil.sleep(1000);
        System.out.println(t.getState());*/

        Thread t1 = new Thread(()->{
            synchronized (lock){
                System.out.println("线程1获取锁 lock 并进入休眠");
                ThreadUtil.sleep(5000000);
            }
        });
        Thread t2 = new Thread(()->{
            synchronized (lock){
                System.out.println("线程3获取锁 lock");
            }
        });
        t1.start();
        // 保证线程1先执行
        ThreadUtil.sleep(1000);
        t2.start();
        // 线程2状态为 TIMED_WAITING，不会释放锁。所以线程3不能获取锁，状态为 BLOCKED。
        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }
}
