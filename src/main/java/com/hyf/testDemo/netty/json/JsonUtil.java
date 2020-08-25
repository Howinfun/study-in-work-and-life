package com.hyf.testDemo.netty.json;

import com.alibaba.fastjson.JSON;
import com.google.gson.GsonBuilder;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/25
 */
public class JsonUtil {


    public static String convertToJson(Object o){
        return new GsonBuilder().create().toJson(o);
    }

    public static <T>T parseFromJson(String json,Class<T> clazz){
        T t = JSON.parseObject(json,clazz);
        return t;
    }
}
