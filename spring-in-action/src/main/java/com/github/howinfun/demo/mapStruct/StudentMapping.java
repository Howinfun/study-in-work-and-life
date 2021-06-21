package com.github.howinfun.demo.mapStruct;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author winfun
 * @date 2021/6/21 10:40 上午
 **/
@Mapper
public interface StudentMapping {

    StudentMapping INSTANCE = Mappers.getMapper(StudentMapping.class);

    @Mapping(target = "name",source = "name")
    @Mapping(target = "age",expression = "java( java.lang.Integer.toString(student.getAge()))")
    StudentVO convertStudent(Student student);

    List<StudentVO> convertStudents(List<Student> students);
}
