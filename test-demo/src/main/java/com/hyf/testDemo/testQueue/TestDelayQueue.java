package com.hyf.testDemo.testQueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/1
 */
public class TestDelayQueue {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<UserMsg> delayQueue = new DelayQueue();
        UserMsg userMsg1 = new UserMsg(1,"15627272727","你好，下单成功1",5, TimeUnit.SECONDS);
        UserMsg userMsg2 = new UserMsg(2,"15627272727","你好，下单成功2",3, TimeUnit.SECONDS);
        UserMsg userMsg3 = new UserMsg(3,"15627272727","你好，下单成功3",4, TimeUnit.SECONDS);
        UserMsg userMsg4 = new UserMsg(4,"15627272727","你好，下单成功4",6, TimeUnit.SECONDS);
        UserMsg userMsg5 = new UserMsg(5,"15627272727","你好，下单成功5",2, TimeUnit.SECONDS);
        delayQueue.add(userMsg1);
        delayQueue.put(userMsg2);
        delayQueue.put(userMsg3);
        delayQueue.put(userMsg4);
        delayQueue.put(userMsg5);

        for (int i=0;i<5;i++){
            UserMsg userMsg = delayQueue.take();
            System.out.println(userMsg.toString());
        }
    }
}
