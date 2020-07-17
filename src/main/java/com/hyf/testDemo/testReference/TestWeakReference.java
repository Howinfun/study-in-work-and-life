package com.hyf.testDemo.testReference;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/17
 */
public class TestWeakReference {

    public static void main(String[] args) {

        /**
         * JVM 参数：-Xms3m -Xmx4m -Xmn2m
         */
        List<WeakReference> list = new ArrayList<>(10);
        for (int i =0; i<=10; i++){
            // 1M 大小的字节数组
            byte[] bytes = new byte[1024 * 1024];
            WeakReference<byte[]> softReference = new WeakReference<>(bytes);
            list.add(softReference);
        }

        list.forEach((sr -> System.out.println(sr.get())));
    }
}
