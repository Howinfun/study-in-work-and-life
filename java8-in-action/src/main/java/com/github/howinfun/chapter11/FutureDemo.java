package com.github.howinfun.chapter11;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.thread.ThreadUtil;
import java.util.concurrent.*;

/**
 * @author: winfun
 * @date: 2021/4/24 4:54 下午
 **/
public class FutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNamePrefix("demo-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        Future<Integer> result1 = pool.submit(FutureDemo::getResult1);
        Integer num2 = getResult2();
        // 方法二已经搞定，直接等待方法1的结果
        System.out.println("成功获取数字2，等待数字1返回....");
        Integer num1 = result1.get(1,TimeUnit.MINUTES);
        System.out.println("最终结果："+(num1+num2));
        pool.shutdown();
    }

    private static int getResult1(){
        System.out.println("正在获取数字1......");
        ThreadUtil.safeSleep(3000);
        System.out.println("返回数字1......");
        return 1;
    }

    private static int getResult2(){
        System.out.println("正在获取数字2......");
        ThreadUtil.safeSleep(1000);
        System.out.println("返回数字2......");
        return 2;
    }
}
