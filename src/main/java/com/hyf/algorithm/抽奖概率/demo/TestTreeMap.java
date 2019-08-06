package com.hyf.algorithm.抽奖概率.demo;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/2
 */
public class TestTreeMap{
    public static void main(String[] args) {
        test();
    }


    private static void test(){
        Integer hasOne = 0;
        Integer hasTwo = 0;
        Integer hasThree = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            /**
             * 需求：转盘抽奖中奖率：一等奖10%  二等奖20%  三等奖70%
             * 使用权重随机算法。我们可以将中奖一共看成100份，然后[0,10)是一等奖，[10,30)是二等奖，[30,100)是三等奖
             * 刚好10-0/100是10%，30-10/100是20%  100-30/100是70%
             * 接下来我们利用TreeMap来完成
             */
            TreeMap<Double,String> treeMap = new TreeMap();
            treeMap.put(10d,"一等奖");
            treeMap.put(30d,"二等奖");
            treeMap.put(100d,"三等奖");
            // 随机数
            Double random = treeMap.lastKey()*Math.random();
            // 因为随机数Math*random()是大于等于0.0且小于1.0
            SortedMap<Double,String> resultMap = treeMap.tailMap(random,false);
            //System.out.println("中奖："+resultMap.get(resultMap.firstKey()));
            // 因为tailMap返回的结果是升序排序的，所以我们取第一个即可
            /*if ("三等奖".equals(resultMap.get(resultMap.firstKey()))){
                hasThree++;
            }else if ("二等奖".equals(resultMap.get(resultMap.firstKey()))){
                hasTwo++;
            }else if ("一等奖".equals(resultMap.get(resultMap.firstKey()))){
                hasOne++;
            }*/
        }
        /*System.out.println("一等奖："+hasOne);
        System.out.println("二等奖："+hasTwo);
        System.out.println("三等奖："+hasThree);*/
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
    }
}
