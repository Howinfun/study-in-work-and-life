package com.winfun.entity.pojo;

import com.winfun.contants.ApiContants;
import lombok.Data;

/**
 * Api Result
 * @author winfun
 * @date 2020/10/30 3:49 下午
 **/
@Data
public class ApiResult<T> {

    /**
     * code：0成功 1失败
     */
    private Integer code = ApiContants.SUCCESS;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回结果
     */
    private T result;
    /**
     * traceId
     */
    private String traceId;
}
