package com.hyf.testDemo.lock;

import cn.hutool.core.util.RandomUtil;

import java.util.LinkedList;
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
public class TestCondition {

    private static Lock lock = new ReentrantLock();
    private static Condition consumeCondition = lock.newCondition();
    private static Condition produceCondition = lock.newCondition();
    private static LinkedList<Integer> queue = new LinkedList<>();
    private static int maxSize = 5;

    static public void consume(){
        try {
            // 获取锁
            lock.lock();
            while (queue.size() == 0){
                // 如果没物品了，消费者则等待
                consumeCondition.await();
            }
            // 模拟消费者每消费一个物品需要1s
            Thread.sleep(1000);
            Integer result = queue.poll();
            System.out.println("消费："+result);
            // 唤醒生产者（此时生产者可能在等待状态）
            produceCondition.signal();
        }catch (Exception e){
            System.out.println("消费出错");
        }finally {
            // 释放锁
            lock.unlock();
        }
    }

    static void produce(){
        try {
            // 获取锁
            lock.lock();
            while (queue.size() == maxSize){
                produceCondition.await();
            }
            // 模拟生产者每生产一个物品需要2s
            Thread.sleep(2000);
            Integer result = RandomUtil.randomInt(100);
            queue.add(result);
            System.out.println("生产了："+result);
            // 唤醒消费者（此时消费者可能在等待状态）
            consumeCondition.signal();
        }catch (Exception e){
            System.out.println("消费出错");
        }finally {
            // 释放锁
            lock.unlock();
        }
    }

    static class Consumer implements Runnable{

        @Override
        public void run() {
            while (true){
                consume();
            }
        }
    }

    static class Producer implements Runnable{

        @Override
        public void run() {
            while (true){
                produce();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();
    }

}
