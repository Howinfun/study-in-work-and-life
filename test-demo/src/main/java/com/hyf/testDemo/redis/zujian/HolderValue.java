package com.hyf.testDemo.redis.zujian;

import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2020/4/7
 */
@Data
public class HolderValue<T> {

    private T value;

    public HolderValue() {
    }

    public HolderValue(T value) {
        this.value = value;
    }
}
