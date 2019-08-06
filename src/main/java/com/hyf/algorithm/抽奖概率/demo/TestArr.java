package com.hyf.algorithm.抽奖概率.demo;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
public class TestArr {
    public static void main(String[] args) {
        Integer[][] arrs = new Integer[3][2];
        arrs[0] = new Integer[]{0,10};
        arrs[1] = new Integer[]{10,30};
        arrs[2] = new Integer[]{30,100};
        Integer hasOne = 0;
        Integer hasTwo = 0;
        Integer hasThree = 0;
        for (int i = 0; i < 100000000; i++) {
            // 随机数
            Double random = 100*Math.random();
            for (int j = 0; j < arrs.length; j++) {
                if (random >= arrs[j][0] && random < arrs[j][1]){
                    // 一等奖概率
                    if (j==0){
                        //System.out.println("中奖一等奖");
                        hasOne ++;
                    }else if (j==1){
                        //System.out.println("中间二等奖");
                        hasTwo++;
                    }else if (j==2){
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
