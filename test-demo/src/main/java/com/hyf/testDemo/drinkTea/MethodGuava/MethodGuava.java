package com.hyf.testDemo.drinkTea.MethodGuava;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/7
 */
public class MethodGuava {

    public static void main(String[] args) {

        DrinkJob drinkJob = new DrinkJob();
        Thread drinkThread = new Thread(drinkJob);
        drinkThread.setName("喝茶程序");
        drinkThread.start();
        ExecutorService jPool = Executors.newFixedThreadPool(2);
        ListeningExecutorService gPool = MoreExecutors.listeningDecorator(jPool);

        ListenableFuture<Boolean> cleanFuture = gPool.submit(new CleanJob());
        ListenableFuture heatFuture = gPool.submit(new HeatJob());
        Futures.addCallback(cleanFuture, new FutureCallback<Boolean>() {

            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                System.out.println(Thread.currentThread().getName()+"：我洗完茶叶了！");
                drinkJob.cleanOk = true;
            }

            @Override
            public void onFailure(Throwable throwable) {
                drinkJob.cleanOk = false;
            }
        },gPool);
        Futures.addCallback(heatFuture, new FutureCallback<Boolean>() {

            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                System.out.println(Thread.currentThread().getName()+"：我烧完水了！");
                drinkJob.heatOk = true;
            }

            @Override
            public void onFailure(Throwable throwable) {
                drinkJob.heatOk = false;
            }
        },gPool);




    }
}
