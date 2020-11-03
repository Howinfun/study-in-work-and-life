package com.winfun.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.winfun.entity.enums.GenderEnum;
import com.winfun.entity.enums.LogRecordEnum;
import com.winfun.entity.enums.UserTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private LogRecordEnum logType;
    private String beforeRecord;
    private String afterRecord;
}
