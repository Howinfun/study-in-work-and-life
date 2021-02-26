package com.winfun.log.sdk.aop;

import com.winfun.log.sdk.entity.enums.LogRecordEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Student Controller
 * @author winfun
 * @date 2020/11/3 4:32 下午
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogRecordAnno {

    /**
     * 日志类型一：记录记录实体
     * Mapper Class，需要配合 MybatisPlus 使用
     */
    Class mapperName();

    /**
     * 日志类型一：记录记录实体
     * 主键
     */
    String id() default "";

    /**
     * 日志类型
     */
    LogRecordEnum logType() default LogRecordEnum.INSERT;

    /**
     * 操作者
     */
    String operator() default "";

    /**
     * 日志类型二：记录日志信息
     * 成功信息
     */
    String successMsg() default "";

    /**
     * 日志类型二：记录日志信息
     * 失败信息
     */
    String errorMsg() default "";
}
