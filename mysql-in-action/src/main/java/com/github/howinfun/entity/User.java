package com.github.howinfun.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: winfun
 * @date: 2021/7/3 12:11 下午
 **/
@Data
@Accessors(chain = true)
public class User {

    private Long id;

    private String name;

    private String gender;

    private String userType;
}
