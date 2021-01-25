package com.winfun.entity.pojo;

import com.winfun.contants.ApiContants;
import lombok.Data;

import java.io.Serializable;

/**
 * Api Result
 * @author winfun
 * @date 2020/10/30 3:49 下午
 **/
@Data
public class ApiResult<T> implements Serializable {

    /**
     * code：0成功 1失败
     */
    private Integer code = ApiContants.SUCCESS;
    /**
     * 返回信息
     */
    private String message = "SUCCESS";
    /**
     * 返回结果
     */
    private T data;
    /**
     * traceId
     */
    private String traceId;

    /**
     * 构造函数
     * @param data
     */
    public ApiResult(T data){
        this.data = data;
    }

    /**
     * 构造函数
     * @param message
     * @param data
     */
    public ApiResult(String message,T data){
        this.message = message;
        this.data = data;
    }

    /**
     * 构造函数
     * @param code
     * @param message
     * @param data
     */
    public ApiResult(Integer code,String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * success
     * @return
     */
    public static ApiResult success(){
        return success(null);
    }

    /**
     * success
     * @param data
     * @return
     */
    public static ApiResult success(Object data){
        return new ApiResult(data);
    }

    /**
     * fail
     * @return
     */
    public static ApiResult fail(){
        return fail(null);
    }

    /**
     * fail
     * @param data
     * @return
     */
    public static ApiResult fail(Object data){
        return fail(ApiContants.FAIL,"fail",data);
    }

    public static ApiResult fail(String message){
        return fail(ApiContants.FAIL,message,null);
    }

    /**
     * fail
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ApiResult fail(Integer code,String message,Object data){
        return new ApiResult(code,message,data);
    }
}
