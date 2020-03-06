package com.hyf.testDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/6
 */
public class ThreadCreate {

    static class TestThread extends Thread{

        @Override
        public void run() {
            System.out.println("Hello");
        }
    }

    static class TestRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("Hello");
        }
    }

    static class TestCallable implements Callable<String>{

        @Override
        public String call() throws Exception {
            return "Hello";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*TestThread t = new TestThread();
        t.start();*/
        /*TestRunnable runnable = new TestRunnable();
        new Thread(runnable).start();*/
        TestCallable callable = new TestCallable();
        FutureTask<String> future = new FutureTask(callable);
        new Thread(future).start();
        System.out.println(future.get());
    }
}
