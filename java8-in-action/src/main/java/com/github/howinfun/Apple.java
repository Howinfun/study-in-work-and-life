package com.github.howinfun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * apple
 * @author winfun
 * @date 2021/4/1 6:22 下午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Apple {

    private String name;
    private Integer weight;
    private String color;

    public Apple(String name){
        this.name = name;
    }
}
