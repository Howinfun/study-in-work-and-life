package com.github.howinfun;

/**
 * 触发新生代GC
 * @author: winfun
 * @date: 2021/5/30 11:18 上午
 **/
public class Demo1 {

    public static void main(String[] args) {

        /**
         * gc 策略：
         -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760
         -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
         -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
         */
        byte[] array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];
        array1 = null;
        byte[] array2 = new byte[2*1024*1024];
    }
}
