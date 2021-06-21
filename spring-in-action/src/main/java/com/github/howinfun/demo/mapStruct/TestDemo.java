package com.github.howinfun.demo.mapStruct;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author winfun
 * @date 2021/6/21 10:42 上午
 **/
public class TestDemo {

    public static void main(String[] args) {
        Student student = new Student().setId(1L).setName("winfun").setAge(1);
        StudentVO studentVO = StudentMapping.INSTANCE.convertStudent(student);
        System.out.println(studentVO);

        List<Student> students = new ArrayList<>();
        students.add(new Student().setId(1L).setName("winfun").setAge(1));
        students.add(new Student().setId(2L).setName("pengpeng").setAge(2));
        List<StudentVO> vos = StudentMapping.INSTANCE.convertStudents(students);
        vos.forEach(System.out::println);
    }
}
