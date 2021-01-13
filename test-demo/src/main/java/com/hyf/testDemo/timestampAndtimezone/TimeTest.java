package com.hyf.testDemo.timestampAndtimezone;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * 测试
 * @author winfun
 * @date 2021/1/13 11:26 上午
 **/
public class TimeTest {

    public static void getTime(String timezone){
        // 默认取当前机器所在时区的日期&时间（北京时间 GMT+8）
        LocalDateTime beijingNow = LocalDateTime.now();
        System.out.println("北京时间："+beijingNow);
        // 转时间戳
        Long beijingTimestamp = beijingNow.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("北京时间获取时间戳："+beijingTimestamp);
        // 根据时间获取莫斯科日期&时间（GMT+3）
        Instant instant = Instant.ofEpochMilli(beijingTimestamp);
        ZoneId zone = ZoneId.of(timezone);
        LocalDateTime moscowNow = LocalDateTime.ofInstant(instant,zone);
        System.out.println("莫斯科时间："+moscowNow);
    }


    public static void getStartTimeAndToGMT8ByTimezone(String timezone){
        // 获取指定时区的当前时间
        LocalDate now = LocalDate.now(ZoneId.of(timezone));
        // 获取上面时间的当天0点0分
        LocalDateTime startTime = LocalDateTime.of(now, LocalTime.MIN);
        // 转成目标时区<GMT+8>对应的时间戳
        Long gmt8Timestamp = startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("The Date of Timezone{"+timezone+"} is " + now + ",Thd StartTime of Timezone{"+timezone+
                                   "} is,"+ startTime +
                                   ",convert to GMT+8 timestamp is "+gmt8Timestamp);

    }

    public static void getEndTimeAndToGMT8ByTimezone(String timezone){
        // 获取指定时区的当前时间
        LocalDate now = LocalDate.now(ZoneId.of(timezone));
        // 获取上面时间的当天23点59分
        LocalDateTime endTime = LocalDateTime.of(now, LocalTime.MAX);
        // 转成目标时区<GMT+8>对应的时间戳
        Long gmt8Timestamp = endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("The Date of Timezone{"+timezone+"} is " + now + ",The EndTime of Timezone{"+timezone+
                                   "} is"+ endTime +
                                   ", convert to GMT+8 timestamp is "+gmt8Timestamp);

    }

    public static void getTimeAndToGMT8ByTimezone(String timezone){
        // 获取指定时区的当前时间
        ZoneId sourceZoneId = ZoneId.of(timezone);
        LocalDateTime now = LocalDateTime.now(sourceZoneId);
        System.out.println(now);
        /**
         * 转成指定时区对应的时间戳
         * 1、根据zoneId获取zoneOffset
         * 2、利用zoneOffset转成时间戳
         */
        ZoneOffset offset = sourceZoneId.getRules().getOffset(now);
        Long timestamp = now.toInstant(offset).toEpochMilli();
        System.out.println(timestamp);
        // 转成北京时间
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId targetZoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime gmt8Now = LocalDateTime.ofInstant(instant,targetZoneId);
        System.out.println(gmt8Now);
        ZoneOffset gmt8Offset = targetZoneId.getRules().getOffset(gmt8Now);
        Long gmt8Timestamp = gmt8Now.toInstant(gmt8Offset).toEpochMilli();
        System.out.println(gmt8Timestamp);
    }

    public static void main(String[] args) {
        String timezone = "Europe/Moscow";
        //getTime(timezone);
        //getStartTimeAndToGMT8ByTimezone(timezone);
        //getEndTimeAndToGMT8ByTimezone(timezone);
        getTimeAndToGMT8ByTimezone(timezone);
    }
}
