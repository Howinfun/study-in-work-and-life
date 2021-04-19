package com.github.howinfun.chapter01;

import com.github.howinfun.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤测试
 * @author winfun
 * @date 2021/4/1 6:21 下午
 **/
public class PrintTest {

    public static List<Apple> apples = new ArrayList<>(10);
    static{
        apples.add(Apple.builder().name("apple1").weight(100).color("green").build());
        apples.add(Apple.builder().name("apple2").weight(60).color("red").build());
        apples.add(Apple.builder().name("apple3").weight(150).color("green").build());
        apples.add(Apple.builder().name("apple4").weight(90).color("red").build());
        apples.add(Apple.builder().name("apple5").weight(111).color("green").build());
    }

    public static void main(String[] args) {
        printApples(apples,apple -> {
            if ("green".equals(apple.getColor())){
                return apple.getName()+","+apple.getColor();
            }
            return "";
        });

        printApples(apples, Apple::getName);
    }

    public static void printApples(List<Apple> sourceList,ApplePrint predicate){
        sourceList.forEach(apple -> System.out.println(predicate.print(apple)));
    }
}
