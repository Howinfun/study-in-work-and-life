package com.hyf.testDemo.mybatis.testEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 用户类型
 * @author winfun
 * @date 2020/10/28 3:27 下午
 **/
@Getter
@AllArgsConstructor
public enum UserTypeEnum{

    STUDENT("01","学生"),
    TEACHER("02","教师"),
    ENGINNER("03","程序员");
    @EnumValue
    private String key;
    @JsonValue
    private String value;

    private static Map<String, UserTypeEnum> userTypeEnumHashMap = new HashMap<>();

    static {
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            userTypeEnumHashMap.put(userTypeEnum.getKey(), userTypeEnum);
        }
    }
    @JsonCreator
    public static UserTypeEnum getSexEnumByKey(String key) {
        return userTypeEnumHashMap.get(key);
    }
}
