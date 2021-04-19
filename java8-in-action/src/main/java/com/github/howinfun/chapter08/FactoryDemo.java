package com.github.howinfun.chapter08;

import com.github.howinfun.Apple;
import com.github.howinfun.Student;
import com.github.howinfun.Trader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工厂模式demo
 * @author winfun
 * @date 2021/4/12 10:20 上午
 **/
public class FactoryDemo {

    public static Map<String, Supplier<Object>> map = new HashMap<>(5);

    static {
        map.put("apple",Apple::new);
        map.put("student", Student::new);
        map.put("trader", Trader::new);
    }

    public static Object getObject(String name){

        Supplier<Object> supplier = map.get(name);
        if (null!=supplier){
            return supplier.get();
        }
        throw new IllegalArgumentException("传入产品名称有误");
    }

    public static void main(String[] args) {
        Apple apple = (Apple) getObject("apple");
        System.out.println(apple);
        Student student = (Student) getObject("student");
        System.out.println(student);
        Trader trader = (Trader) getObject("trader");
        System.out.println(trader);
    }
}
