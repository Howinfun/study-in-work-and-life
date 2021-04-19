package com.github.howinfun.chapter03;

import com.github.howinfun.Apple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author winfun
 * @date 2021/4/3 4:39 下午
 **/
public class SortDemo {

    public static List<Apple> apples = new ArrayList<>(10);
    static{
        apples.add(Apple.builder().name("apple1").weight(100).color("green").build());
        apples.add(Apple.builder().name("apple2").weight(100).color("red").build());
        apples.add(Apple.builder().name("apple3").weight(150).color("green").build());
        apples.add(Apple.builder().name("apple4").weight(90).color("red").build());
        apples.add(Apple.builder().name("apple5").weight(111).color("green").build());

    }

    public static void main(String[] args) {

        List<Apple> result =  apples.stream().sorted(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        }).collect(Collectors.toList());
        result.forEach(System.out::println);

        result = apples.stream().sorted((apple1,apple2) -> apple1.getWeight().compareTo(apple2.getWeight())).collect(Collectors.toList());
        result.forEach(System.out::println);

        // Comparator的排序是升序，所以反转一下；接着，如果重量一样，根据颜色排序
        result =
                apples.stream().sorted(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor)).collect(Collectors.toList());
        result.forEach(System.out::println);

    }
}
