package com.github.howinfun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易记录
 * @author winfun
 * @date 2021/4/6 11:46 上午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private Trader trader;
    private int year;
    private int value;
}
