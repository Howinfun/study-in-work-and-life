package com.hyf.testDemo.thread;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/31
 */
public class ThreadPool {

    public static final ExecutorService threadPool1 = Executors.newFixedThreadPool(100);//9917 8814
    public static final ExecutorService threadPool2 = Executors.newFixedThreadPool(50);//10909 11150
    public static final ExecutorService threadPool3 = Executors.newFixedThreadPool(10);//4755 6368

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 1;i<=10000000;i++){
            threadPool1.execute(new Task("任务"+i));
        }
        long end = System.currentTimeMillis();
        System.out.println("一共使用了"+(end-start)+"毫秒");
    }

    static class Task implements Runnable{

        private String name;

        public Task(String name){
            this.name = name;
        }

        @Override
        public void run() {
            //System.out.println("我是任务【"+name+"】，我开始执行了~");
            // 随机休眠0~1s
            ThreadUtil.sleep(RandomUtil.randomInt(1), TimeUnit.SECONDS);
            //System.out.println("我是任务【"+name+"】，我执行完毕了~");
        }
    }
}
