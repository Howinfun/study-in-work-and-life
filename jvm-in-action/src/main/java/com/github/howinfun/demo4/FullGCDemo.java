package com.github.howinfun.demo4;

/**
 * full gc
 * @author winfun
 * @date 2021/6/1 9:06 上午
 **/
public class FullGCDemo {


    public static void main(String[] args) {
        /**
            -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
            -XX:PretenureSizeThreshold=3145728
            -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log

            新生代10M：eden区8M，两个survivor分别是1M
            老年代10M
            大对象阈值：3M

         gc日志：

         CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:OldPLABSize=16 -XX:PretenureSizeThreshold=3145728 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
         0.098: [GC (Allocation Failure) 0.099: [ParNew (promotion failed): 7853K->8404K(9216K), 0.0033683 secs]0.102: [CMS: 8194K->6654K(10240K), 0.0023250 secs] 11949K->6654K(19456K), [Metaspace: 3036K->3036K(1056768K)], 0.0058162 secs] [Times: user=0.02 sys=0.01, real=0.01 secs]
         Heap
         par new generation   total 9216K, used 2214K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
         eden space 8192K,  27% used [0x00000007bec00000, 0x00000007bee29808, 0x00000007bf400000)
         from space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
         to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
         concurrent mark-sweep generation total 10240K, used 6654K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
         Metaspace       used 3057K, capacity 4496K, committed 4864K, reserved 1056768K
         class space    used 334K, capacity 388K, committed 512K, reserved 1048576K

         gc 分析：
         1、创建4M的字节数组，因为大于大对象阈值，所以直接分配到老年代
         2、array1变量指向null，4M字节数组变为垃圾对象
         3、连续创建三个2M的字节数组，都分配到新生代的eden区，此时eden已经有6M+大小的对象
         4、继续创建一个128k的字节数组，也是直接分配到新生代的eden区，此时eden已经有6M+128k+大小的对象
         5、上面创建的三个2M的字节数组和一个128k的字节数组都是存活对象
         6、最后创建一个2M的字节数组，因为eden区总共大小为8M，已无法再容纳一个2M大的对象，所以触发了一次young gc，由于eden区里面的对象都是存活对象（3个2M的字节数组+1个128k的字节数组），并且survivor无法容纳这些对象，所以需要分配到老年代
            6.1 老年代一共10M，而此时已经有一个4M的字节数组，当存放2个2M的字节数组后，容量已经到了8M+，所以当分派第三个2M的字节数组时，发现已经无法容纳，此时会触发CMS的full gc
            6.2 由于4M的字节数组是垃圾对象，所以此时会将它回收掉，此时老年代还剩下两个存活的2M字节数组
            6.3 接着存放第三个2M的字节数组和128k的字节数组，最后老年代使用了6M+128k+的大小
         7、full gc 后，老年代已使用6M+128k+的大小，eden区也是空的，所以最后一个2M的字节数组会继续成功分派到eden区中。

         */

        // 指定4M字节数组给array1变量，直接被分派到老年代
        byte[] array1 = new byte[4 * 1024 * 1024];
        array1 = null;

        byte[] array2 = new byte[2 * 1024 * 1024];
        byte[] array3 = new byte[2 * 1024 * 1024];
        byte[] array4 = new byte[2 * 1024 * 1024];
        byte[] array5 = new byte[128 * 1024];

        byte[] array6 = new byte[2 * 1024 * 1024];
    }
}
