package com.hyf.testDemo.thread;

import cn.hutool.core.thread.ThreadUtil;

/**
 * @author Howinfun
 * @desc 测试线程阻塞
 * @date 2020/3/6
 */
public class ThreadJoin {

    private final static Object lock = new Object();
    private volatile static boolean flag = true;
    public static void main(String[] args) throws InterruptedException {


        /**
         * join 方法。调用其他线程的join方法，变味执行其他线程，自己进入WAITING状态，直到其他线程执行完毕
         */
        // 线程2 调用 线程1 的 join 方法，没有进入 waiting 状态，只是陷入了 join() 里面的 while 死循环出不来
        /*Thread thread1 = new Thread(()->{
            while (true){
                ThreadUtil.sleep(1000);
                System.out.println("线程1");
            }
        });

        Thread thread2 = new Thread(()->{
            try {
                int i = 0;
                while (true){
                    i++;
                    ThreadUtil.sleep(1000);
                    if (i == 10){
                        // 当调用线程1的 join方法，线程2会等待线程1执行完毕，才会继续执行
                        thread1.join();
                    }
                    System.out.println("线程2");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
        ThreadUtil.sleep(1000);
        thread1.start();*/
        // join 释放锁案例
        Thread t1 = new Thread(()->{
            while (flag){
                ThreadUtil.sleep(1000);
                System.out.println("线程1");
            }
        });
        Thread t2 = new Thread(()->{
            synchronized (t1){
                try {
                    int i = 0;
                    while (true){
                        i++;
                        ThreadUtil.sleep(1000);
                        if (i == 3){
                            // 当调用线程1的 join方法，线程2会等待线程1执行完毕，才会继续执行
                            System.out.println("线程2调用线程1的 join() 方法，释放锁 t1");
                            t1.join(1000);
                            System.out.println("join() 方法执行完毕，线程2重新获取锁 t1");
                        }
                        System.out.println("线程2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(()->{
            synchronized (t1){
                // System.out.println("线程3获取锁t1进入同步代码块，线程2的状态为："+t2.getState());
                /*int j = 0;
                while (j <= 3){
                    ThreadUtil.sleep(1000);
                    System.out.println("线程3");
                    j++;
                }
                // 最后修改变量 flag
                System.out.println("线程3执行完毕前修改变量 flag 为 false，让线程1停止执行；然后释放锁；接着线程2会重新获取锁执行方法");
                flag = false;*/

                System.out.println("线程3获取锁t1进入同步代码块，线程2的状态为："+t2.getState());
                int j = 0;
                while (true){
                    j++;
                    if (j == 3){
                        System.out.println("修改共享变量 flag 为 false，让线程1停止执行");
                        flag = false;
                    }
                    ThreadUtil.sleep(1000);
                    System.out.println("线程3，线程2的状态为："+t2.getState());
                }
            }
        });
        t1.start();
        t2.start();
        // 保证线程2在线程3之前执行
        ThreadUtil.sleep(1000);
        t3.start();

        // 不是放锁对象
        /*Thread t1 = new Thread(()->{
            while (flag){
                ThreadUtil.sleep(1000);
                System.out.println("线程1");
            }
        });
        Thread t2 = new Thread(()->{
            synchronized (lock){
                try {
                    int i = 0;
                    while (true){
                        i++;
                        ThreadUtil.sleep(1000);
                        if (i == 3){
                            // 当调用线程1的 join方法，线程2会等待线程1执行完毕，才会继续执行
                            System.out.println("线程2调用线程1的 join() 方法，不会释放锁 lock");
                            t1.join();
                            System.out.println("join() 方法执行完毕");
                        }
                        System.out.println("线程2");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(()->{
            synchronized (lock){
                System.out.println("线程3获取锁 lock");
                ThreadUtil.sleep(2000);
                System.out.println("修改共享变量 flag 为 false，让线程1停止执行");
                flag = false;
            }
        });
        t1.start();
        t2.start();
        // 保证线程2在线程3之前执行
        ThreadUtil.sleep(1000);
        t3.start();*/
    }
}
