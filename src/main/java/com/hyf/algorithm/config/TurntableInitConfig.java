package com.hyf.algorithm.config;

import com.hyf.algorithm.mapper.TurntableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
@Configuration
public class TurntableInitConfig {
    @Autowired
    private TurntableMapper turntableMapper;



}
