package com.github.howinfun.demo1;

/**
 * 触发新生代GC
 * @author: winfun
 * @date: 2021/5/30 11:18 上午
 **/
public class YoungGCDemo {

    public static void main(String[] args) {

        /**
         1、gc 策略：
         -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760
         -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
         -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log

         GC 策略分析：
         整个堆的大小为10M，新生代分配5M，老年代分配5M；新生代中：eden区和survivor的比例为8：1：1，即eden区有4.5m，每个survivor都为0.5M


         2、GC日志：

         CommandLine flags: -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:MaxNewSize=5242880 -XX:NewSize=5242880 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
         0.110: [GC (Allocation Failure) 0.110: [ParNew: 3411K->393K(4608K), 0.0016366 secs] 3411K->1419K(9728K), 0.0017351 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         Heap
         par new generation   total 4608K, used 3627K [0x00000007bf600000, 0x00000007bfb00000, 0x00000007bfb00000)
         eden space 4096K,  78% used [0x00000007bf600000, 0x00000007bf928920, 0x00000007bfa00000)
         from space 512K,  76% used [0x00000007bfa80000, 0x00000007bfae2558, 0x00000007bfb00000)
         to   space 512K,   0% used [0x00000007bfa00000, 0x00000007bfa00000, 0x00000007bfa80000)
         concurrent mark-sweep generation total 5120K, used 1026K [0x00000007bfb00000, 0x00000007c0000000, 0x00000007c0000000)
         Metaspace       used 2928K, capacity 4496K, committed 4864K, reserved 1056768K
         class space    used 319K, capacity 388K, committed 512K, reserved 1048576K

         GC日志分析：
         第一行是打印我们进程使用的JVM参数
         第二行是GC日志的打印：
            0.110表示当进程运行0.110秒后进行了一次GC，其中是young gc，新生代一共4608K，gc的时候，回收垃圾对象后从3411k到393k，即最后新生代的存活对象为393k；
            而后面的3411K->1419K(9728K)是显示整个堆内存的gc情况，整个堆一共9728k，gc后，从3411k到1419k，我们可以看到，gc后的存活对象竟然还有1419k，证明，gc的时机并不是array2变量的赋值，而是将第三个1M字节数组指定给array1变量的时候。
            在进程启动时，就有一些对象被分配在新生代，当第二个1M字节数组被指定给array1变量后，新生代eden区此时已使用3411k的内存，而一共eden区内存只有4608k，当将第三个1M字节数组指定给array1变量时，明显eden区已经容纳不了1024k的对象了，所以进行了一次young gc；
            由于young gc时，只有第一个1M字节数组是垃圾对象，而第二个1M字节数组是存活对象，所以第一个1M字节数组被垃圾回收了，而由于survivor无法容纳1M的存活对象，所以第二个1M字节数组被分配到老年代了。
         Heap 下面的就是gc后的堆内存使用情况。
            新生代总共4608k，已使用3627k
            eden区总共4096k，已使用78%
            survivor from区总共512K，已使用76%
            survivor to区总共512K，已使用0%
            老年代总共5120k，已使用1026k
            metaspace。。。。
            class space。。。。

         */
        byte[] array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];
        array1 = new byte[1024*1024];
        array1 = null;
        byte[] array2 = new byte[2*1024*1024];
    }
}
