package com.hyf.testDemo.thread;

import cn.hutool.core.thread.ThreadUtil;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/20
 */
public class ThreadSuspendResume {

    private final static Object lock = new Object();

    public static void main(String[] args) {
        /**
         * 线程阻塞：线程2调用线程1的 suspend() 方法，此时线程1的状态为：TIMED_WAITING，如果有锁，不会释放锁。
         *         当线程2调用线程1的 resume() 方法时，线程1才会重新执行。
         *
         */

         Thread thread1 = new Thread(()->{

             synchronized (lock){
                 while (true){
                     ThreadUtil.sleep(1000);
                     System.out.println("线程1");
                 }
             }
         });

         Thread thread2 = new Thread(()->{

             thread1.suspend();
             System.out.println("我来阻塞线程1了");
             System.out.println("线程1的状态："+thread1.getState());
             ThreadUtil.sleep(20000);
             thread1.resume();

         });

         Thread thread3 = new Thread(()->{

             synchronized (lock){
                 while (true){
                     ThreadUtil.sleep(1000);
                     System.out.println("线程3");
                 }
             }
         });

         thread1.start();
         ThreadUtil.sleep(1000);
         thread2.start();
         ThreadUtil.sleep(1000);
         thread3.start();


    }
}
