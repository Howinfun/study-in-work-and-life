package com.github.howinfun.demo.ioc.bean_definition;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: winfun
 * @date: 2021/7/11 9:51 上午
 **/
@Data
@Accessors(chain = true)
public class Cat {
    private Person person;
}
