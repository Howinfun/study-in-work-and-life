package com.hyf.testDemo.testReference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/17
 */
public class TestPhantomReference {

    public static void main(String[] args) {

        /**
         * JVM 参数：-Xms3m -Xmx4m -Xmn2m
         */
        ReferenceQueue referenceQueue = new ReferenceQueue();
        new Thread(()->{
            while (true) {
                try {
                    Object obj = referenceQueue.poll();
                    if (null != obj) {
                        System.out.println(obj);
                        Field rereferent = Reference.class
                                .getDeclaredField("referent");
                        rereferent.setAccessible(true);
                        Object result = rereferent.get(obj);
                        System.out.println("gc will collect："
                                + result.getClass() + "@"
                                + result.hashCode() + "\t" + result);
                    }
                }catch (Exception e){

                }
            }
        }).start();
        List<PhantomReference> list = new ArrayList<>(10);
        for (int i =0; i<=10; i++){
            // 1M 大小的字节数组
            byte[] bytes = new byte[1024 * 1024];
            PhantomReference<byte[]> softReference = new PhantomReference<>(bytes,referenceQueue);
            list.add(softReference);
        }
        System.gc();
        list.forEach((sr -> System.out.println(sr.get())));
    }
}
