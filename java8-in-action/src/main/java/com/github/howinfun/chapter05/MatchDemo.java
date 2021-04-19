package com.github.howinfun.chapter05;

import com.github.howinfun.Apple;

import java.util.ArrayList;
import java.util.List;

/**
 * 匹配demo
 * 短路求值，不需要处理整个流就可以得到结果
 * @author winfun
 * @date 2021/4/6 10:43 上午
 **/
public class MatchDemo {

    public static List<Apple> apples = new ArrayList<>(10);
    static{
        apples.add(Apple.builder().name("apple1").weight(100).color("green").build());
        apples.add(Apple.builder().name("apple2").weight(60).color("red").build());
        apples.add(Apple.builder().name("apple3").weight(150).color("green").build());
        apples.add(Apple.builder().name("apple4").weight(90).color("red").build());
        apples.add(Apple.builder().name("apple5").weight(111).color("green").build());
    }
    public static void main(String[] args) {
        // 是否存在重量大于100的苹果
        boolean anyMatchFlag = apples.stream().anyMatch(a -> {
            System.out.println("遍历到苹果："+a.getName()+",重量为："+a.getWeight());
            return a.getWeight() > 100;
        });
        System.out.println("是否存在重量大于100的苹果："+anyMatchFlag);

        // 是否所有苹果都是绿色
        boolean allMatchFlag = apples.stream().allMatch(a -> {
            System.out.println("遍历到苹果："+a.getName()+",颜色为："+a.getColor());
            return "green".equals(a.getColor());
        });
        System.out.println("是否所有苹果都是绿色："+allMatchFlag);

        // 是否所有苹果都不是红色
        boolean noneMatchFlag = apples.stream().noneMatch(a -> {
            System.out.println("遍历到苹果："+a.getName()+",颜色为："+a.getColor());
            return "red".equals(a.getColor());
        });
        System.out.println("是否所有苹果都不是红色："+noneMatchFlag);
    }
}
