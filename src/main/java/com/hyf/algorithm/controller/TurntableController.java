package com.hyf.algorithm.controller;

import com.hyf.algorithm.entity.TurntablePrize;
import com.hyf.algorithm.service.TurntableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
@RestController
@RequestMapping("/turntable")
public class TurntableController {

    @Autowired
    private TurntableService turntableService;

    @GetMapping("/draw")
    public TurntablePrize turntableDraw(@RequestParam("phone") String phone){
        return turntableService.turntableDraw(phone);
    }
}
