package com.github.howinfun.demo.mapStruct;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author winfun
 * @date 2021/6/21 10:36 上午
 **/
@Data
@Accessors(chain = true)
public class Student {

    private Long id;
    private String name;
    private Integer age;
}
