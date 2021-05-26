package com.github.howinfun.datedemo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

/**
 *
 * @author winfun
 * @date 2021/5/26 10:32 上午
 **/
public class TimezoneTest {

    private static Integer num = 1 * 60 * 60 * 1000;

    public static void main(String[] args) {
        /**
         * 根据timezone查询offset
         * Asia/Shanghai  -> +08:00
         */
        String timezone = "Asia/Shanghai";
        ZoneOffset zoneOffset = ZoneId.of(timezone).getRules().getOffset(LocalDateTime.now());
        String offset = zoneOffset.toString();
        System.out.println(offset);
        String timezone2 = "America/Recife";
        /**
         * 获取timezone到UTC的时间间隔 ->  mills  -> 小时
         */
        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.of(timezone2));
        int rawOffset = timeZone.getRawOffset();
        System.out.println(rawOffset/num);

    }
}
