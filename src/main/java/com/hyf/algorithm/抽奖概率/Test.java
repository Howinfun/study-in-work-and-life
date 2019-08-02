package com.hyf.algorithm.抽奖概率;

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
        for (int i = 0; i < 100000000; i++) {
            // 一等奖概率
            Integer one = RandomUtil.randomInt(1,100);
            if (one>=1 && one <=10){
                //System.out.println("中奖一等奖");
                hasOne ++;
            }else{
                Integer two = RandomUtil.randomInt(11,100);
                if (two>=11 && two<=30){
                    //System.out.println("中间二等奖");
                    hasTwo++;
                }else{
                    Integer three = RandomUtil.randomInt(31,100);
                    if (three>=31 && three<=100){
                        //System.out.println("中奖三等奖");
                        hasThree++;
                    }
                }
            }
        }
        System.out.println("一等奖中奖次数："+hasOne);
        System.out.println("二等奖中奖次数："+hasTwo);
        System.out.println("三等奖中奖次数："+hasThree);
    }
}
