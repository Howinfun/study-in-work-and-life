package com.github.howinfun.sentinel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 属性变更类型
 * @author winfun
 * @date 2021/3/10 5:27 下午
 **/
@Getter
@AllArgsConstructor
public enum PropertyChangeType {

    /**
     * 新增
     */
    ADDED,
    /**
     * 修改
     */
    MODIFIED,
    /**
     * 删除
     */
    DELETED;
}
