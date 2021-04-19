package com.hyf.algorithm.抽奖概率.demo;

import cn.hutool.core.util.RandomUtil;

/**
 * @author Howinfun
 * @desc 抽奖概率
 * @date 2019/8/2
 */
public class Test {
    public static void main(String[] args) {
        Integer hasOne = 0;
        Integer hasTwo = 0;
        Integer hasThree = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            // 一等奖概率
            Integer random = RandomUtil.randomInt(1,100);
            if (random>=1 && random <=10){
                //System.out.println("中奖一等奖");
                //hasOne ++;
            }else if (random>=11 && random<=30){
                    //System.out.println("中间二等奖");
                    //hasTwo++;
            }else if (random>=31 && random<=100){
                        //System.out.println("中奖三等奖");
                        //hasThree++;
            }
        }
        /*System.out.println("一等奖中奖次数："+hasOne);
        System.out.println("二等奖中奖次数："+hasTwo);
        System.out.println("三等奖中奖次数："+hasThree);*/
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
    }
}
