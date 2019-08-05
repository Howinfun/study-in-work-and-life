package com.hyf.algorithm.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
@Data
@ToString
public class TurntablePrize {
    private Integer id;
    private Integer drawId;
    private String prizeName;
    private Double weight;
}
