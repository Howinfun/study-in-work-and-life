package com.hyf.testDemo.lock;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/14
 */
public class TestSynchronized {

    private int s = 0;
    public static void main(String[] args) throws Exception{

        TestSynchronized testSynchronized = new TestSynchronized();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread t1 = new Thread(()->{
            try {
                ThreadUtil.sleep(3000);
                System.out.println("线程1到达栅栏");
                cyclicBarrier.await();
                System.out.println("线程1冲破栅栏");
                testSynchronized.method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            try {
                ThreadUtil.sleep(1000);
                System.out.println("线程2到达栅栏");
                cyclicBarrier.await();
                System.out.println("线程2冲破栅栏");
                ThreadUtil.sleep(500);
                testSynchronized.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }

    public void method1(){

        //线程1
        synchronized(TestSynchronized.class){
            s++;
            s++;
            s++;
        }
    }

    public void method2(){
        //线程2
        ThreadUtil.sleep(1000);
        System.out.println(s);
    }
}
