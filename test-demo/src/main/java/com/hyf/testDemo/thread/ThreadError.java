package com.hyf.testDemo.thread;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/6
 */
public class ThreadError {

    public static void main(String[] args) {
        Thread t = new Thread(()->{
            System.out.println("Hello!");
            throw new RuntimeException("我出错了~");
        });
        t.setUncaughtExceptionHandler((t1, e) -> {
            System.out.println("线程1："+e.getMessage());
            System.out.println("出错你就拉倒吧！");
        });
        t.start();
    }
}
