package com.hyf.algorithm.抽奖概率.common;

import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/6
 */
@Data
public class Result<T> {
    private Integer code = Constant.SUCCESS;
    private String msg;
    private T data;

    public Result(){

    }

    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }
}
