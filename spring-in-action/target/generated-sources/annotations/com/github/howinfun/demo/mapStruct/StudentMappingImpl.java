package com.github.howinfun.demo.mapStruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-18T08:58:43+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
public class StudentMappingImpl implements StudentMapping {

    @Override
    public StudentVO convertStudent(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentVO studentVO = new StudentVO();

        studentVO.setName( student.getName() );

        studentVO.setAge( java.lang.Integer.toString(student.getAge()) );

        return studentVO;
    }

    @Override
    public List<StudentVO> convertStudents(List<Student> students) {
        if ( students == null ) {
            return null;
        }

        List<StudentVO> list = new ArrayList<StudentVO>( students.size() );
        for ( Student student : students ) {
            list.add( convertStudent( student ) );
        }

        return list;
    }
}
