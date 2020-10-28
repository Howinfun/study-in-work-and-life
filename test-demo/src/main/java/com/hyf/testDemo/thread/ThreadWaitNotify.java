package com.hyf.testDemo.thread;

import cn.hutool.core.thread.ThreadUtil;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/20
 */
public class ThreadWaitNotify {
    /** 锁对象 */
    private final static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        /**
         * Object的 wait()方法和 notify() 方法，是配合 synchronized 关键字使用的。调用 wait() 方法会释放对应的锁。
         */
        Thread thread1 = new Thread(()->{

            synchronized (lock){
                int i = 0;
                while (i <= 10){
                    ThreadUtil.sleep(1000);
                    System.out.println("线程1");
                    i++;
                }
                try {
                    System.out.println("线程1停止了");
                    lock.wait();
                    //lock.wait(2000); 如果是带超时等待时间，线程的状态为TIME_WAITING，不带时间为WAITING
                    System.out.println("线程1又开始了");
                    while (true){
                        ThreadUtil.sleep(1000);
                        System.out.println("线程1");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(()->{

            synchronized (lock){
                System.out.println("线程1的状态："+thread1.getState());
                int i = 0;
                while (i <= 5){
                    ThreadUtil.sleep(1000);
                    System.out.println("线程2");
                    i++;
                }
                lock.notify();
            }
        });
        thread1.start();
        ThreadUtil.sleep(3000);
        thread2.start();

    }
}
