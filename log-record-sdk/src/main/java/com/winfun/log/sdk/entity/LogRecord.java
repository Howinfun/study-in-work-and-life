package com.winfun.log.sdk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.winfun.log.sdk.entity.enums.LogTypeEnum;
import com.winfun.log.sdk.entity.enums.SqlTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LogRecord
 * @author winfun
 * @date 2020/11/3 5:18 下午
 **/
@Data
@Accessors(chain = true)
@TableName("log_record")
public class LogRecord implements Serializable {
    private static final long serialVersionUID = -3282942985909442971L;
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    /**
     * 业务名称
     */
    private String businessName;
    /**
     * 操作日志类型：message ｜ record
     */
    private LogTypeEnum logType;
    /**
     * SQL类型：1增2改3删
     */
    private SqlTypeEnum sqlType;
    /**
     * 方法执行前数据库记录内容
     */
    private String beforeRecord;
    /**
     * 方法执行后数据库记录内容
     */
    private String afterRecord;
    /**
     * 操作者
     */
    private String operator;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 成功消息
     */
    private String successMsg;
    /**
     * 失败消息
     */
    private String errorMsg;
}
