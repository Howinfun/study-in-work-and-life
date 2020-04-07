package com.hyf.testDemo.redis.zujian;

import lombok.Data;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2020/4/7
 */
public class LRUTest {

    @Data
    static class LRU<String,Object> extends LinkedHashMap<String,Object>{

        private int maxCapacity;

        public LRU(float loadFactor, int maxCapacity){
            // accessOrder 插入是按顺序插入，查询也会变为带顺序（意思是，被查询的节点，会往后面移动）
            super(maxCapacity,loadFactor,true);
            this.maxCapacity = maxCapacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
            // 移除最旧节点的判断条件
            return size() > maxCapacity;
        }
    }



    public static void main(String[] args) {
        LRU lru = new LRU(0.75f,4);
        lru.put("k1","哈哈1");
        lru.put("k2","哈哈2");
        lru.put("k3","哈哈3");
        lru.put("k4","哈哈4");
        System.out.println("初始化数据：");
        Iterator iterator = lru.values().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("查询一下");
        System.out.println(lru.get("k1"));
        System.out.println(lru.get("k3"));
        System.out.println(lru.get("k4"));
        System.out.println("再新增数据：k5");
        lru.put("k5","哈哈5");
        Iterator iterator2 = lru.values().iterator();
        while (iterator2.hasNext()){
            System.out.println(iterator2.next());
        }
    }
}
