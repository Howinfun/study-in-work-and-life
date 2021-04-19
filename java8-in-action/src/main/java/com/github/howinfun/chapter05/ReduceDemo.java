package com.github.howinfun.chapter05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author winfun
 * @date 2021/4/6 11:23 上午
 **/
public class ReduceDemo {

    public static void main(String[] args) {
        // 数字列表求总和
        List<Integer> numList = Arrays.asList(1,3,4,5,6);
        Integer result = numList.stream().reduce(0,Integer::sum);
        System.out.println(result);

        numList = new ArrayList<>();
        Optional<Integer> optionalInteger = numList.stream().reduce(Integer::sum);
        System.out.println(optionalInteger.orElse(0));

    }
}
