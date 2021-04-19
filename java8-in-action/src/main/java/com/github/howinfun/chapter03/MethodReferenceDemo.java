package com.github.howinfun.chapter03;

import com.github.howinfun.Apple;

import java.util.function.Function;

/**
 *
 * @author winfun
 * @date 2021/4/3 4:05 下午
 **/
public class MethodReferenceDemo {

    public static void main(String[] args) {


        Function<String, Apple> appleFunction = Apple::new;
        Apple apple = appleFunction.apply("好苹果");
        System.out.println(apple);

        appleFunction = name -> Apple.builder().name(name).build();
        apple = appleFunction.apply("坏苹果");
        System.out.println(apple);
    }
}
