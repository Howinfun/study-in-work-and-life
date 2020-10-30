package com.winfun.entity.dto;

import lombok.Data;

/**
 * Dubbo API Response
 * @author winfun
 * @date 2020/10/30 2:14 下午
 **/
@Data
public class DubboResponseDTO {

    /**
     * 接口名称
     */
    private String interfaceClassName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 结果
     */
    private String result;
    /**
     * 耗时（单位毫秒）
     */
    private Long spendTime;
}
