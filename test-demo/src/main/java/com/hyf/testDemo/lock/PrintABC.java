package com.hyf.testDemo.lock;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/7
 * @company WCWC
 */
public class PrintABC {

    private static Lock lock = new ReentrantLock();
    private static Condition ACondition = lock.newCondition();
    private static Condition BCondition = lock.newCondition();
    private static Condition CCondition = lock.newCondition();
    // Condition要配合等待条件使用才行。
    private static int flag = 0;

    static public void printA(){

        try {
            lock.lock();
            while (flag !=0){
                ACondition.await();
            }
            ThreadUtil.sleep(1000);
            System.out.println("A");
            flag = 1;
            BCondition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    static public void printB(){

        try {
            lock.lock();
            while (flag != 1){
                BCondition.await();
            }
            ThreadUtil.sleep(1000);
            System.out.println("B");
            flag = 2;
            CCondition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    static public void printC(){

        try {
            lock.lock();
            while (flag != 2){
                CCondition.await();
            }
            ThreadUtil.sleep(1000);
            System.out.println("C");
            flag = 0;
            ACondition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    static class ARunnable implements Runnable{

        @Override
        public void run() {
            while (true){
                printA();
            }
        }
    }

    static class BRunnable implements Runnable{

        @Override
        public void run() {
            while (true){
                printB();
            }
        }
    }

    static class CRunnable implements Runnable{

        @Override
        public void run() {
            while (true){
                printC();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ARunnable()).start();
        ThreadUtil.sleep(1000);
        new Thread(new BRunnable()).start();
        ThreadUtil.sleep(1000);
        new Thread(new CRunnable()).start();
    }
}
