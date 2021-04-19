package com.github.howinfun.chapter03;

import com.github.howinfun.Apple;
import com.github.howinfun.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Predicate函数接口
 * @author winfun
 * @date 2021/4/3 11:15 上午
 **/
public class PredicateDemo {

    public static List<Apple> apples = new ArrayList<>(10);
    public static List<Student> students = new ArrayList<>(10);
    static{
        apples.add(Apple.builder().name("apple1").weight(100).color("green").build());
        apples.add(Apple.builder().name("apple2").weight(60).color("red").build());
        apples.add(Apple.builder().name("apple3").weight(150).color("green").build());
        apples.add(Apple.builder().name("apple4").weight(90).color("red").build());
        apples.add(Apple.builder().name("apple5").weight(111).color("green").build());

        students.add(Student.builder().name("winfun").sex("boy").height(172).build());
        students.add(Student.builder().name("luff").sex("boy").height(33).build());
        students.add(Student.builder().name("fy").sex("girl").height(160).build());
        students.add(Student.builder().name("kaka").sex("girl").height(189).build());
    }

    public static void main(String[] args) {
        System.out.println("绿色的苹果：");
        // 绿色的苹果
        List<Apple> appleList = filterList(apples,(Apple a) -> "green".equals(a.getColor()));
        appleList.forEach(System.out::println);

        System.out.println("男同学：");
        // 男的学生
        List<Student> studentList = filterList(students,(Student s) -> "boy".equals(s.getSex()));
        studentList.forEach(System.out::println);
    }

    private static <T> List<T> filterList(List<T> sourceList, Predicate<T> p){
        return sourceList.stream().filter(p).collect(Collectors.toList());
    }
}
