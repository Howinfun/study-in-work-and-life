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

    /***
     * 对比同一时间戳，不同时区的时间显示
     * @author winfun
     * @param sourceTimezone sourceTimezone
     * @param targetTimezone targetTimezone
     * @return {@link Void }
     **/
    public static void compareTimeByTimezone(String sourceTimezone,String targetTimezone){
        // 当前时间服务器UNIX时间戳
        Long timestamp = System.currentTimeMillis();
        // 获取源时区时间
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId sourceZoneId = ZoneId.of(sourceTimezone);
        LocalDateTime sourceDateTime = LocalDateTime.ofInstant(instant,sourceZoneId);
        // 获取目标时区时间
        ZoneId targetZoneId = ZoneId.of(targetTimezone);
        LocalDateTime targetDateTime = LocalDateTime.ofInstant(instant,targetZoneId);
        System.out.println("The timestamp is "+timestamp+",The DateTime of Timezone{"+sourceTimezone+"} is "+sourceDateTime+
                                   ",The " +
                                   "DateTime of Timezone{"+targetTimezone+"} is "+targetDateTime);
    }

    /***
     * 获取源时区的当前日期的零点零分，转为目标时区对应的时间戳
     * @author winfun
     * @param sourceTimezone 源时区
     * @param targetTimezone 目标时区
     * @return {@link Void }
     **/
    public static void getStartTimeFromSourceTimezoneAndConvertTimestampToTargetTimezone(String sourceTimezone,
                                                                            String targetTimezone){
        // 获取指定时区的当前时间
        ZoneId sourceZoneId = ZoneId.of(sourceTimezone);
        LocalDateTime dateTime = LocalDateTime.now(sourceZoneId);
        LocalDate date = LocalDate.now(sourceZoneId);
        // 获取上面时间的当天0点0分
        LocalDateTime startTime = LocalDateTime.of(date, LocalTime.MIN);
        // 转成目标时区对应的时间戳
        ZoneId targetZoneId = ZoneId.of(targetTimezone);
        ZoneOffset targetZoneOffset = targetZoneId.getRules().getOffset(dateTime);
        Long gmt8Timestamp = startTime.toInstant(targetZoneOffset).toEpochMilli();
        System.out.println("The Date of Timezone{"+sourceTimezone+"} is " + date + ",Thd StartTime of Timezone{"+sourceTimezone+
                                   "} is,"+ startTime +
                                   ",convert to Timezone{"+targetTimezone+"} timestamp is "+gmt8Timestamp);

    }

    /***
     * 获取源时区的当前日期的23点59分，转为目标时区对应的时间戳
     * @author winfun
     * @param sourceTimezone 源时区
     * @param targetTimezone 目标时区
     * @return {@link Void }
     **/
    public static void getEndTimeFromSourceTimezoneAndConvertTimestampToTargetTimezone(String sourceTimezone,
                                                                                     String targetTimezone){
        // 获取指定时区的当前时间
        ZoneId sourceZoneId = ZoneId.of(sourceTimezone);
        LocalDateTime dateTime = LocalDateTime.now(sourceZoneId);
        LocalDate date = LocalDate.now(sourceZoneId);
        // 获取上面时间的当天23点59分
        LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
        // 转成目标时区对应的时间戳
        ZoneId targetZoneId = ZoneId.of(targetTimezone);
        ZoneOffset targetZoneOffset = targetZoneId.getRules().getOffset(dateTime);
        Long gmt8Timestamp = endTime.toInstant(targetZoneOffset).toEpochMilli();
        System.out.println("The Date of Timezone{"+sourceTimezone+"} is " + date + ",The EndTime of Timezone{"+sourceTimezone+
                                   "} is"+ endTime +
                                   ", convert to Timezone{"+targetTimezone+"} timestamp is "+gmt8Timestamp);

    }

    /***
     * 获取源时区的当前时间，转为目标时区对应的时间戳
     * @author winfun
     * @param sourceTimezone 源时区
     * @param targetTimezone 目标时区
     * @return {@link Void }
     **/
    public static void getTimeFromSourceTimezoneAndConvertToTargetTimezoneToTargetTimezone(String sourceTimezone,
                                                                                           String targetTimezone){
        // 获取指定时区的当前时间
        ZoneId sourceZoneId = ZoneId.of(sourceTimezone);
        LocalDateTime dateTime = LocalDateTime.now(sourceZoneId);
        /**
         * 转成指定时区对应的时间戳
         * 1、根据zoneId获取zoneOffset
         * 2、利用zoneOffset转成时间戳
         */
        ZoneId targetZoneId = ZoneId.of(targetTimezone);
        ZoneOffset offset = targetZoneId.getRules().getOffset(dateTime);
        Long timestamp = dateTime.toInstant(offset).toEpochMilli();
        System.out.println("The DateTime of Timezone{"+sourceTimezone+"} is " + dateTime + ",convert to Timezone{"+targetTimezone+"} timestamp is "+timestamp);
    }

    public static void main(String[] args) {
        String sourceTimezone = "Europe/Moscow";
        String targetTimezone = "Asia/Shanghai";
        compareTimeByTimezone(sourceTimezone,targetTimezone);
        getStartTimeFromSourceTimezoneAndConvertTimestampToTargetTimezone(sourceTimezone,targetTimezone);
        getEndTimeFromSourceTimezoneAndConvertTimestampToTargetTimezone(sourceTimezone,targetTimezone);
        getTimeFromSourceTimezoneAndConvertToTargetTimezoneToTargetTimezone(sourceTimezone,targetTimezone);
    }
}
