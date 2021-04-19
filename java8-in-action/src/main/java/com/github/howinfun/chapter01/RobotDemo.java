package com.github.howinfun.chapter01;

import java.util.function.Consumer;

/**
 * 机器人Demo
 * @author winfun
 * @date 2021/4/8 10:33 上午
 **/
public class RobotDemo {

    public static void shopping(String someThing){
        System.out.println("调用shopping方法，让机器人购物，购买: "+someThing);
    }

    public static void fetchPackage(String orderNum){
        System.out.println("调用fetchPackage方法，让机器人取快递，快递单号: "+orderNum);
    }

    public static void doSomeThing(String param,Consumer<String> consumer){
        System.out.print("调用doSomeThing方法，");consumer.accept(param);
    }

    public static void main(String[] args) {
        System.out.println("jdk8 之前，机器人干活。。。。");
        shopping("苹果");
        shopping("香蕉");
        fetchPackage("order123");
        fetchPackage("order234");

        System.out.println("jdk8 后，机器人干活");
        doSomeThing("苹果",v->System.out.println("让机器人购物，购买："+v));
        doSomeThing("香蕉",v->System.out.println("让机器人购物，购买："+v));
        doSomeThing("order123",v->System.out.println("让机器人取快递，快递单号: "+v));
        doSomeThing("order234",v->System.out.println("让机器人取快递，快递单号: "+v));
    }
}
