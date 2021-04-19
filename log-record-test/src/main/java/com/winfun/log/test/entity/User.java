package com.winfun.log.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.winfun.log.test.entity.enums.GenderEnum;
import com.winfun.log.test.entity.enums.UserTypeEnum;
import lombok.Data;
import lombok.ToString;

/**
 * 用户
 * @author winfun
 * @date 2020/10/28 4:07 下午
 **/
@Data
@ToString
@TableName("user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String name;
    private GenderEnum gender;
    private UserTypeEnum userType;
}
