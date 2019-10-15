package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc
 * @date 2019/10/15
 */
public class 裴波那契数列 {

    /**
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
     * 1 1 2 3 5 8 13 21 34 .....
     * f(1) = 1, f(2) = 1, f(n) = f(n-1) + f(n-2) [n>=3]
     * n<=39
     * @param n
     * @return
     */
    public static int fibonacci(int n) {
        int result = 0;
        if (n <= 0){
            return 0;
        }
        if (n == 1 || n == 2){
            return 1;
        }
        // 当n大于等于3，利用递归求出第n项的值
        if (n >= 3){
            result = fibonacci(n-1)+fibonacci(n-2);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(fibonacci(6));
    }
}
