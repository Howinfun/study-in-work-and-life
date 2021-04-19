package com.github.howinfun.chapter05;

import com.github.howinfun.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author winfun
 * @date 2021/4/6 11:23 上午
 **/
public class MapReduceDemo {

    public static List<Apple> apples = new ArrayList<>(10);
    static{
        apples.add(Apple.builder().name("apple1").weight(100).color("green").build());
        apples.add(Apple.builder().name("apple2").weight(60).color("red").build());
        apples.add(Apple.builder().name("apple3").weight(150).color("green").build());
        apples.add(Apple.builder().name("apple4").weight(90).color("red").build());
        apples.add(Apple.builder().name("apple5").weight(111).color("green").build());
    }

    public static void main(String[] args) {
        // 将苹果的名称弄成一个列表
        String nameStr = apples.stream().map(Apple::getName).reduce("", String::concat);
        System.out.println(nameStr);
    }
}
