package com.github.howinfun.chapter01;

import com.github.howinfun.Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 过滤测试
 * @author winfun
 * @date 2021/4/1 6:21 下午
 **/
public class FilterTest {

    public static List<Apple> apples = new ArrayList<>(10);
    static{
        apples.add(Apple.builder().name("apple1").weight(100).color("green").build());
        apples.add(Apple.builder().name("apple2").weight(60).color("red").build());
        apples.add(Apple.builder().name("apple3").weight(150).color("green").build());
        apples.add(Apple.builder().name("apple4").weight(90).color("red").build());
        apples.add(Apple.builder().name("apple5").weight(111).color("green").build());
    }

    public static void main(String[] args) {
        // lambda
        List<Apple> resultList = filterApples(apples,a-> a.getWeight()>100 || a.getColor().equals("red"));
        resultList.forEach(System.out::println);

        System.out.println("------------------------");
        // 自定义Predicate
        resultList = filterApples(apples,new AppleColorPredicate());
        resultList.forEach(System.out::println);
    }

    public static List<Apple> filterApples(List<Apple> sourceList,ApplePredicate predicate){
        return sourceList.stream().filter(apple -> predicate.filter(apple)).collect(Collectors.toList());
    }
}
