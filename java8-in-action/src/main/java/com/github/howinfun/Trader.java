package com.github.howinfun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易员
 * @author winfun
 * @date 2021/4/6 11:45 上午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trader {

    private String name;
    private String city;
}
