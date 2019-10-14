package com.hyf.algorithm.抽奖概率.service.impl;

import com.hyf.algorithm.抽奖概率.config.TurntableDrawUtils;
import com.hyf.algorithm.抽奖概率.entity.TurntablePrize;
import com.hyf.algorithm.抽奖概率.service.TurntableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
@Service
public class TurntableServiceImpl implements TurntableService {

    @Autowired
    private TurntableDrawUtils drawUtils;

    @Override
    public TurntablePrize turntableDraw(String phone) {
        return drawUtils.turntableDraw(phone);
    }
}
