package com.github.howinfun.demo.beantype;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

/**
 * @author: winfun
 * @date: 2021/6/19 4:49 下午
 **/
@Data
@Accessors(chain = true)
public class Child {

    private String wantBuy;
}
