package com.winfun.log.sdk.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * LogRecordEnum
 * @author winfun
 * @date 2020/11/3 4:36 下午
 **/
@Getter
@AllArgsConstructor
public enum LogRecordEnum {

    INSERT("1","新增"),
    UPDATE("2","更新"),
    DELETE("3","删除");

    @EnumValue
    private String key;
    @JsonValue
    private String value;

    private static Map<String, LogRecordEnum> logRecordEnumHashMap = new HashMap<>();

    static {
        for (LogRecordEnum logRecordEnum : LogRecordEnum.values()) {
            logRecordEnumHashMap.put(logRecordEnum.getKey(), logRecordEnum);
        }
    }
    @JsonCreator
    public static LogRecordEnum getGenderEnumByKey(String key) {
        return logRecordEnumHashMap.get(key);
    }
}
