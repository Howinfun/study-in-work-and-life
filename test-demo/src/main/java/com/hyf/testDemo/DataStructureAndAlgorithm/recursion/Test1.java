package com.hyf.testDemo.DataStructureAndAlgorithm.recursion;

/**
 * @author Howinfun
 * @desc 斐波那契数列 -> 递归
 * @date 2020/8/13
 */
public class Test1 {

    public static void main(String[] args) {
        System.out.println(f(4));
    }

    public static int f(int n){
        if (n==0){
            return n;
        }
        if (n == 1) {
            return n;
        }
        return f(n-1)+f(n-2);
    }
}
