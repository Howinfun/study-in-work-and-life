package com.github.howinfun;

import java.applet.AppletContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生
 * @author winfun
 * @date 2021/4/3 11:14 上午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = -4200544289970811842L;

    private String name;
    private String sex;
    private Integer height;

    private Apple apple;
}
