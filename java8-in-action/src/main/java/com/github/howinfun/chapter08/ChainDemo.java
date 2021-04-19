package com.github.howinfun.chapter08;

import java.util.function.UnaryOperator;

/**
 * 责任链demo
 * @author winfun
 * @date 2021/4/12 9:47 上午
 **/
public class ChainDemo {

    public static void main(String[] args) {
        UnaryOperator<String> headerProcessor = (String text) -> "hello, "+text;
        UnaryOperator<String> endProcessor = (String text) -> text + " end.";

        String result = headerProcessor.andThen(endProcessor).apply("hahaha");
        System.out.println(result);
    }
}
