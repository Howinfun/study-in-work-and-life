package com.github.howinfun.demo.ioc.program;

import lombok.Data;

/**
 *
 * @author winfun
 * @date 2021/7/15 9:23 上午
 **/
@Data
public abstract class Animal {
    private String name;
    private Person person;
}
