package com.hyf.testDemo.queue;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2020/3/7
 * @company WCWC
 */
public class TestArrayBlockingQueue {

    /** 阻塞队列 */
    private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    static public void consume(){
        try {
            // 模拟描述消费一个
            ThreadUtil.sleep(RandomUtil.randomInt(10)*100);
            // take()方法会一直阻塞，知道队列不为空
            Integer result = queue.take();
            System.out.println("消费了："+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static public void produce(){
        try {
            // put()方法如果碰到队列已满，会一直阻塞
            Integer result = RandomUtil.randomInt(10);
            // 模拟随你描述才生产一个
            ThreadUtil.sleep(RandomUtil.randomInt(10)*100);
            System.out.println("生产了："+result);
            queue.put(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        // 不再需要特别注意谁先开始
        new Thread(new Consumer()).start();
        new Thread(new Producer()).start();
    }
}
