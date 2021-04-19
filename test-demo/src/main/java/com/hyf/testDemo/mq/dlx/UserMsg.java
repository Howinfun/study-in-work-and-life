package com.hyf.testDemo.mq.dlx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMsg {

    private int id;
    private String phone;
    private String msg;
    private int failCount;
}
