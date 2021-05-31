package com.github.howinfun.demo3;

/**
 * young gc 之 超过survivor容量的对象直接进入老年代
 * @author: winfun
 * @date: 2021/5/30 11:18 上午
 **/
public class YoungGCDemo3 {

    public static void main(String[] args) {

        /**
         1、gc 策略：
         -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760
         -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
         -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log

         GC 策略分析：
         整个堆的大小为10M，新生代分配5M，老年代分配5M；新生代中：eden区和survivor的比例为8：1：1，即eden区有4.5m，每个survivor都为0.5M
         */
        byte[] array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];

        byte[] array2 = new byte[128*1024];
        // 在分配2M字节数组给array3时，由于eden区已无法容纳，所以此时会触发一次young gc
        // 由于survivor区的大小为0.5M，所以只能容纳array2变量指向的128k字节数组，而array1变量指向的1M字节数组只能被分派到老年代中
        byte[] array3 = new byte[2*1024*1024];
    }
}
