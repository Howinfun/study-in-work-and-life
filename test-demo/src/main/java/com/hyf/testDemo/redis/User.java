package com.hyf.testDemo.redis;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/24
 */
@Data
@TableName("user")
public class User implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private String phone;
}
