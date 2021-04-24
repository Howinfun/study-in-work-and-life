package com.github.howinfun.chapter09;

import com.github.howinfun.Apple;
import com.github.howinfun.SchoolBag;
import com.github.howinfun.Student;
import java.applet.Applet;
import java.applet.AppletContext;
import java.util.Optional;

/**
 * @author: winfun
 * @date: 2021/4/24 9:22 上午
 **/
public class OptionalDemo {

    public static void main(String[] args) {
        Student student = null;
        Student result = Optional.ofNullable(student).orElse(Student.builder().build());
        System.out.println(result);

        // 过滤学生是否拥有红色的苹果
        Student student2 = Student.builder().name("winfun").apple(Apple.builder().name("red apple").color("red").build()).build();
        Student result2 = Optional.of(student2).filter(s -> "red".equals(s.getApple().getColor())).orElse(null);
        System.out.println(result2);

        // 获取学生的苹果，如果没有则返回null
        Student student3 = Student.builder().build();
        Apple apple =Optional.ofNullable(student2).map(Student::getApple).orElse(null);
        System.out.println(apple);

        // 直接获取学生的书包
        Student student4 = Student.builder().name("winfun").schoolBag(Optional.ofNullable(SchoolBag.builder().name("winfun 's schoolbag").build())).build();
        SchoolBag schoolBag = Optional.of(student4).flatMap(Student::getSchoolBag).get();
        System.out.println(schoolBag);
    }
}
