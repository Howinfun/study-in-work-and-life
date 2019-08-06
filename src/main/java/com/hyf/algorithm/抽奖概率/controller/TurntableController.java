package com.hyf.algorithm.抽奖概率.controller;

import com.hyf.algorithm.抽奖概率.common.Result;
import com.hyf.algorithm.抽奖概率.entity.TurntablePrize;
import com.hyf.algorithm.抽奖概率.service.TurntableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Howinfun
 * @desc 抽奖Controller
 * @date 2019/8/5
 */
@RestController
@RequestMapping("/turntable")
public class TurntableController {

    @Autowired
    private TurntableService turntableService;

    @GetMapping("/draw")
    public Result<TurntablePrize> turntableDraw(@RequestParam("phone") String phone){
        Result result = new Result();
        TurntablePrize prize =  turntableService.turntableDraw(phone);
        result.setData(prize);
        if (prize == null){
            result.setMsg("奖品已抽完");
        }else {
            result.setMsg("恭喜获得"+prize.getPrizeName());
        }
        return result;
    }
}
