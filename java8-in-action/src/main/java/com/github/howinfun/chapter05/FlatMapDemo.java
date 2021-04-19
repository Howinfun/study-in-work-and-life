package com.github.howinfun.chapter05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 给定单词列表，找出唯一的字符
 * @author winfun
 * @date 2021/4/6 9:33 上午
 **/
public class FlatMapDemo {

    public static void main(String[] args) {
        System.out.println("-------- map -----------");
        // 第一种，直接使用map，失败
        List<String> words = new ArrayList<>(2);
        words.add("hello");
        words.add("world");
        List<String[]> stringList =
               words.stream().map(str->str.split("")).distinct().collect(Collectors.toList());
        stringList.forEach(line-> Arrays.stream(line).forEach(System.out::print));
        System.out.println("map后返回的是Stream<String[]>，所以无法求出所有单词的唯一字符，需要利用flatMap对流进行扁平化");
        System.out.println("-------- flatMap ----------");
        // 使用 flatMap
        List<String> resultList =
                words.stream().map(str -> str.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        resultList.forEach(System.out::print);
    }
}
