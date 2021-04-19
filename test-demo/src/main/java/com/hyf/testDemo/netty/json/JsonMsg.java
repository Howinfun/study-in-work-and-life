package com.hyf.testDemo.netty.json;

import com.alibaba.fastjson.JSON;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/25
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JsonMsg {

    private Integer id;
    private String content;

    public String convertToJson(){
        return new GsonBuilder().create().toJson(this);
    }

    public static JsonMsg parseFromJson(String json){
        return JSON.parseObject(json,JsonMsg.class);
    }
}
