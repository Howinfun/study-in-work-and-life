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
public class TurntableDraw {
    private Integer id;
    private String drawName;
    private Double weight;
    private Integer prizeNum;
}
