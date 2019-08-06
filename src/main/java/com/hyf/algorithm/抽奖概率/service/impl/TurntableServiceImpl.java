package com.hyf.algorithm.service.impl;

import com.hyf.algorithm.config.TurntableDrawInit;
import com.hyf.algorithm.entity.TurntablePrize;
import com.hyf.algorithm.service.TurntableService;
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
    private TurntableDrawInit drawInit;

    @Override
    public TurntablePrize turntableDraw(String phone) {
        return drawInit.turntableDraw(phone);
    }
}
