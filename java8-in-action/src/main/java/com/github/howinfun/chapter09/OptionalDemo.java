package com.github.howinfun.chapter09;

import com.github.howinfun.Apple;
import com.github.howinfun.Student;
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
    }
}
