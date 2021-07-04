package com.github.howinfun.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: winfun
 * @date: 2021/7/4 10:45 上午
 **/
@Data
@Accessors(chain = true)
public class UniqueKeyTest {

    private Long id;
    private String name;
    private Integer age;
}
