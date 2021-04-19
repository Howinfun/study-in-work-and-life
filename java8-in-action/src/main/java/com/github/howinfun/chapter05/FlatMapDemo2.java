package com.github.howinfun.chapter05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 找出对数
 * @author winfun
 * @date 2021/4/6 9:54 上午
 **/
public class FlatMapDemo2 {

    public static void main(String[] args) {
        List<Integer> num1 = Arrays.asList(1,2,3,4);
        List<Integer> num2 = Arrays.asList(3,4);
        List<int[]> resultList =
                num1.stream().flatMap(i -> num2.stream().map(j -> new int[]{i,j})).collect(Collectors.toList());
        resultList.stream().map(Arrays::toString).forEach(System.out::println);
    }
}
