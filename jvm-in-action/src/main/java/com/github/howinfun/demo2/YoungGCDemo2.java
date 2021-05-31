package com.github.howinfun.demo2;

/**
 * yonug gc 之 动态年龄判断
 * @author: winfun
 * @date: 2021/5/31 5:22 下午
 **/
public class YoungGCDemo2 {


    public static void main(String[] args) {
        /**
            jvm 参数：
                -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
                -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
            简单解读：
            整个堆共20M，新生代10M：其中eden区是8M，两个survivor分别是1M


         */
        // 创建第一个2M的字节数组
        byte[] array1 = new byte[2*1024*1024];
        // 创建第二个2M的字节数组，第一个变为垃圾对象
        array1 = new byte[2*1024*1024];
        // 创建第三个2M的字节数组，第二个变为垃圾对象
        array1 = new byte[2*1024*1024];
        array1 = null;
        // 创建一个128k的字节数组，第三个2M的字节数组变为垃圾对象
        byte[] array2 = new byte[512*1024];

        // 先触发一次young gc，128k的字节数组被分派到survivor区中（并且年龄增加1，即变为年龄1），接着再创建新对象
        // TODO   1.8.0_211-b12中没有动态年龄判断
        byte[] array3 = new byte[2*1024*1024];
        array3 = new byte[2*1024*1024];
        array3 = new byte[2*1024*1024];
        array3 = new byte[128*1024];
        array3 = null;

        // 触发第二次young gc
        byte[] array4 = new byte[2*1024*1024];

    }
}
