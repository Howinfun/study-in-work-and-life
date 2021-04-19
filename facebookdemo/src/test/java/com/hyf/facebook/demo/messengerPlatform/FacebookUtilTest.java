package com.hyf.facebook.demo.messengerPlatform;

import org.junit.Test;

/**
 *
 * @author winfun
 * @date 2021/2/22 4:52 下午
 **/

public class FacebookUtilTest {

    public static String accessToken = "";

    @Test
    public void test(){
        FacebookUtil util = new FacebookUtil();
        System.out.println(util.hasGetStarted(accessToken));
    }

}
