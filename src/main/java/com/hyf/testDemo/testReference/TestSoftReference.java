package com.hyf.testDemo.testReference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/17
 */
public class TestSoftReference {

    public static void main(String[] args) {

        /**
         * JVM 参数：-Xms3m -Xmx4m -Xmn2m
         * 堆初始化内存为 3M，最大为 4M；新生代占用 2M，其中 Eden 和 Survivor 的占比是 8(1.6M):1(0.2M):1(0.2M)，老年代 2M
         * 下面代码的流程：
         * 每次循环，创建 1M 大小的实例对象放入，每两次创建，Eden 不够内存存放实例对象，则进行一次 young gc，而 Survivor 更加无法保存 1M 大小的实例对象
         * 那么这个 1M 的实例对象进入了老年代。
         *
         * 当两个 1M 大小的实例对象进入老年代，那么就会触发 full gc，此时会将软引用的实例对象清理掉，那么全部对象都被清理掉了。
         *
         * 所以当最后一次循环后，只剩下一个软引用的实例对象存活！
         */
        List<SoftReference> list = new ArrayList<>(10);
        for (int i =0; i<=10; i++){
            // 1M 大小的字节数组
            byte[] bytes = new byte[1024 * 1024];
            SoftReference<byte[]> softReference = new SoftReference<>(bytes);
            list.add(softReference);
        }

        list.forEach((sr -> System.out.println(sr.get())));
    }
}
