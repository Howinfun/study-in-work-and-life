package com.winfun.log.sdk.aop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winfun.log.sdk.entity.enums.LogTypeEnum;
import com.winfun.log.sdk.entity.enums.SqlTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LogRecord 注解
 * @author winfun
 * @date 2021/2/25 4:32 下午
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogRecordAnno {

    /**
     * 操作日志类型
     * @return
     */
    LogTypeEnum logType() default LogTypeEnum.MESSAGE;

    /**
     * 业务名称
     * @return
     */
    String businessName() default "";
    /**
     * 日志类型一：记录记录实体
     * Mapper Class，需要配合 MybatisPlus 使用
     */
    Class mapperName() default BaseMapper.class;

    /**
     * 日志类型一：记录记录实体
     * 主键
     */
    String id() default "";

    /**
     * sql类型：增删改
     */
    SqlTypeEnum sqlType() default SqlTypeEnum.INSERT;

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
