package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc
 * @date 2019/10/15
 */
public class 二进制中1的个数 {

    public static int numberOf1(int n) {
        int result = 0;
        // int 4个字节 32位
        for (int i = 0; i < 32; i++) {
            // n&1 最后一位0返回0 1返回1
            result += (n&1);
            // 无符号右移一位
            n = n >>> 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(numberOf1(-1));
    }
}
