package com.hyf.testDemo.thread;

import cn.hutool.core.thread.ThreadUtil;

/**
 * @author Howinfun
 * @desc 测试线程阻塞
 * @date 2020/3/6
 */
public class ThreadBlocked {

    private final static Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {


        /**
         * sleep 方法
         */
        Thread t = new Thread(()->{
            while (true){
                ThreadUtil.sleep(10000);
            }
        });
        t.start();
        ThreadUtil.sleep(1000);
        System.out.println(t.getState());
        /**
         * join 方法。调用其他线程的join方法，变味执行其他线程，自己进入WAITING状态，直到其他线程执行完毕
         */
        /*Thread thread1 = new Thread(()->{
            int i = 0;
            while (i <= 10){
                ThreadUtil.sleep(1000);
                System.out.println("线程1");
                i++;
            }
        });

        Thread thread2 = new Thread(()->{
            try {
                int i = 0;
                while (i <= 10){
                    ThreadUtil.sleep(1000);
                    System.out.println("线程2");
                    i++;
                }
                // 当调用线程1的 join方法，线程2会等待线程1执行完毕，才会继续执行
                thread1.join(1000);
                while (true){
                    ThreadUtil.sleep(1000);
                    System.out.println("线程2");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
        ThreadUtil.sleep(3000);
        thread1.start();*/


        /**
         * Object的 wait()方法和 notify() 方法，是配合 synchronized 关键字使用的
         */
        /*Thread thread1 = new Thread(()->{

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
        thread2.start();*/

        /**
         * 线程阻塞：被另外的线程调用此线程的suspend方法，此时线程的状态为：TIMED_WAITING
         *         只有等到另外的线程再次调用此线程的resume方法，此线程才会继续执行
         *

         Thread thread1 = new Thread(()->{
         while (true){
         ThreadUtil.sleep(1000);
         System.out.println("线程1");
         }
         });

         Thread thread2 = new Thread(()->{

         thread1.suspend();
         System.out.println("我来阻塞线程1了");
         System.out.println("线程1的状态："+thread1.getState());
         ThreadUtil.sleep(20000);
         thread1.resume();

         });

         thread1.start();
         // 保证线程1先执行
         ThreadUtil.sleep(5000);
         thread2.start();

         */
    }
}
