package com.hyf.algorithm.抽奖概率.service;

import com.hyf.algorithm.抽奖概率.entity.TurntablePrize;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
public interface TurntableService {

    TurntablePrize turntableDraw(String phone);
}
