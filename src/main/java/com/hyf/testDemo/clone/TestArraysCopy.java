package com.hyf.testDemo.clone;

import java.util.Arrays;

/**
 * @author Howinfun
 * @desc Arrays.copf 是浅克隆，只是将旧数组的引用地址复制了一份给新数组
 * @date 2020/6/3
 */
public class TestArraysCopy {



    public static void main(String[] args){

        User[] u1= {new User(1L,"Howinfun",new Address(1L,"佛山"))};
        User[] u2 = Arrays.copyOf(u1,u1.length);

        u1[0].setName("pengpeng");
        System.out.println("u1:" + u1[0].getName());
        System.out.println("u2:" + u2[0].getName());
    }
}
