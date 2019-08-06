package com.hyf.algorithm.抽奖概率.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
@Data
@ToString
public class TurntableDraw {
    private Integer id;
    private String drawName;
    private Double weight;
    private Integer prizeNum;
}
