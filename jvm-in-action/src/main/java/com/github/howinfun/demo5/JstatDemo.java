package com.github.howinfun.demo5;

import cn.hutool.core.thread.ThreadUtil;
import java.util.concurrent.TimeUnit;

/**
 * 使用jstat命令查看进程堆的使用情况
 * @author winfun
 * @date 2021/6/2 4:25 下午
 **/
public class JstatDemo {

    public static void main(String[] args) {

        /**
         gc 参数：
         -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
         -XX:PretenureSizeThreshold=3145728
         -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log

         新生代10M：eden区8M，两个survivor分别是1M
         老年代10M
         大对象阈值：3M
         */
        // 先休眠30s，给自己点时间查看java进程的进程id，并执行 jstat -gc pid 命令
        ThreadUtil.sleep(30, TimeUnit.SECONDS);

        while (true){
            byte[] data = null;
            for (int i = 0; i < 50; i++) {
                data = new byte[1024];
            }
            data = null;
            ThreadUtil.sleep(1000);
        }
    }
}
