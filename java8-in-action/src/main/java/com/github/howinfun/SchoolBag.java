package com.github.howinfun;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: winfun
 * @date: 2021/4/24 1:16 下午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolBag {

    private String name;
}
