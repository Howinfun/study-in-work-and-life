package com.github.howinfun.sentinel.pojo;

import com.github.howinfun.sentinel.enums.PropertyChangeType;
import lombok.Data;

/**
 * 属性变更内容
 * @author winfun
 * @date 2021/3/10 5:28 下午
 **/
@Data
public class PropertyChange {

    /**
     * 属性名
     */
    private String propertyName;
    /**
     * 旧值
     */
    private String oldValue;
    /**
     * 新值
     */
    private String newValue;
    /**
     * 变更内容
     */
    private PropertyChangeType changeType;
}
