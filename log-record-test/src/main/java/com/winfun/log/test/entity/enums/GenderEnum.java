package com.winfun.log.test.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 性别
 * @author winfun
 * @date 2020/10/28 3:22 下午
 **/
@Getter
@AllArgsConstructor
public enum GenderEnum{

    MALE("1","男性"),
    FEMALE("2","女性");
    @EnumValue
    private String key;
    @JsonValue
    private String value;

    private static Map<String, GenderEnum> genderEnumMap = new HashMap<>();

    static {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            genderEnumMap.put(genderEnum.getKey(), genderEnum);
        }
    }
    @JsonCreator
    public static GenderEnum getGenderEnumByKey(String key) {
        return genderEnumMap.get(key);
    }
}
