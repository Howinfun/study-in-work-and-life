package com.hyf.testDemo.testString;

/**
 * @author Howinfun
 * @desc
 * @date 2020/6/29
 */
public class TestString {

    public static void main(String[] args){
        String s1 = new String("abc");
        String s2 = "abc";
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);
    }
}
