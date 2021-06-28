package com.github.howinfun.demo.ioc.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 篮球场
 * @author winfun
 * @date 2021/6/28 9:10 上午
 **/
@Configuration
public class BasketBallCourt {

    @Bean
    @Profile("sunny")
    public BasketBall basketBall(){
        return new BasketBall();
    }

    @Bean
    @Profile("sunny")
    public People people(){
        return new People();
    }
}
