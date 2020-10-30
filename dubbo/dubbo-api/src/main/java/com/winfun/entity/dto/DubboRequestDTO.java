package com.winfun.entity.dto;

import lombok.Data;

/**
 * Dubbo Api Request
 * @author winfun
 * @date 2020/10/30 2:12 下午
 **/
@Data
public class DubboRequestDTO {

    /**
     * 接口名称
     */
    private String interfaceClass;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数（不包含 byte[],Byte[],inputStream,File）
     */
    private Object[] args;
}
