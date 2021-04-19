package com.github.howinfun.chapter05;

import com.github.howinfun.Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * find demo
 * @author winfun
 * @date 2021/4/6 11:04 上午
 **/
public class FindDemo {

    public static List<Apple> apples = new ArrayList<>(10);
    static{
        apples.add(Apple.builder().name("apple1").weight(100).color("green").build());
        apples.add(Apple.builder().name("apple2").weight(60).color("red").build());
        apples.add(Apple.builder().name("apple3").weight(150).color("green").build());
        apples.add(Apple.builder().name("apple4").weight(90).color("red").build());
        apples.add(Apple.builder().name("apple5").weight(111).color("green").build());
    }

    public static void main(String[] args) {
        // 找到第一个红色的苹果并打印出来
        Optional<Apple> findFirstResult = apples.stream().filter(a -> "red".equals(a.getColor())).findFirst();
        findFirstResult.ifPresent(System.out::println);

        // 找到红色苹果并打印出来，效果其实和上面的一样
        apples.stream().filter(a -> "red".equals(a.getColor())).findAny().ifPresent(System.out::println);
    }

}
