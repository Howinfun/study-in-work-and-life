package com.hyf.testDemo.localdatetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author winfun
 * @date 2021/2/23 9:55 上午
 **/
public class Test {

    public static void main(String[] args) {

        // 获取当前时间的整分
        String timeZone = ZoneId.systemDefault().getId();
        Long zero = getStart(timeZone);
        List<Long> resultList = getOneList(zero);
        for (Long aLong : resultList) {
            System.out.println(aLong);
            /*LocalDateTime localDateTime = Instant.ofEpochMilli(aLong).atOffset(ZoneOffset.of("+8")).toLocalDateTime();
            System.out.println(localDateTime);*/
        }
    }

    /**
     * 获取十分钟间隔时间戳列表
     * @param zero 当天零点时间戳
     * @return
     */
    private static List<Long> getTenList(Long zero){
        // 每十分钟
        List<Long> tenList = new ArrayList<>(200);
        // 十分钟毫秒数
        Long interval = 10 * 60 * 1000L;
        tenList.add(zero);
        for (int i = 0; i < 144; i++) {
            zero += interval;
            tenList.add(zero);
        }
        return tenList;
    }

    /**
     * 获取一分钟间隔时间戳列表
     * @param zero 当天零点时间戳
     * @return
     */
    private static List<Long> getOneList(Long zero){
        // 每十分钟
        List<Long> oneList = new ArrayList<>(200);
        // 十分钟毫秒数
        Long interval = 1 * 60 * 1000L;
        oneList.add(zero);
        for (int i = 0; i < 1440; i++) {
            zero += interval;
            oneList.add(zero);
        }
        return oneList;
    }

    /**
     * 获取开始时间
     * @param timezone
     * @return
     */
    private static Long getStart(String timezone){
        LocalDate now = LocalDate.now(ZoneId.of(timezone));
        LocalDateTime startTime = LocalDateTime.of(now, LocalTime.MIN);
        Long startMillSecond = startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return startMillSecond;
    }

    /**
     * 获取结束时间
     * @param timezone
     * @return
     */
    private static Long getEnd(String timezone){
        LocalDate now = LocalDate.now(ZoneId.of(timezone));
        LocalDateTime endTime = LocalDateTime.of(now,LocalTime.MAX);
        Long endMillSecond = endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return endMillSecond;
    }
}
