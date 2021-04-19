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
public enum LogTypeEnum {

    MESSAGE("message","信息"),
    RECORD("record","实体记录");

    @EnumValue
    private String key;
    @JsonValue
    private String value;

    private static Map<String, LogTypeEnum> logRecordEnumHashMap = new HashMap<>();

    static {
        for (LogTypeEnum logRecordEnum : LogTypeEnum.values()) {
            logRecordEnumHashMap.put(logRecordEnum.getKey(), logRecordEnum);
        }
    }
    @JsonCreator
    public static LogTypeEnum getLogTypeEnumByKey(String key) {
        return logRecordEnumHashMap.get(key);
    }
}
