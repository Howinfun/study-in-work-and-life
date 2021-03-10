package com.winfun.listener;

import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.github.howinfun.sentinel.listener.AbstractSentinelRulesListener;
import com.github.howinfun.sentinel.pojo.PropertyChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author winfun
 * @date 2021/3/10 11:24 上午
 **/
@Slf4j
@Component
public class ApolloSentinelRulesListener extends AbstractSentinelRulesListener {

    private static final String FLOW = "sentinel.rules.flowRuleList";
    private static final String DEGRADE = "sentinel.rules.degradeRuleList";

    @ApolloConfigChangeListener
    private void someChangeHandler(ConfigChangeEvent changeEvent) {
        Set<String> changeKeys = changeEvent.changedKeys();
        Map<Integer, List<PropertyChange>> flowRulesChangeMap = new HashMap<>();
        Map<Integer,List<PropertyChange>> degradeRulesChangeMap = new HashMap<>();
        changeKeys.forEach(key -> {
            /**
             * ConfigChange{namespace='application', propertyName='sentinel.rules.flowRuleList[0].count', oldValue='1', newValue='10', changeType=MODIFIED}
             */
            ConfigChange configChange = changeEvent.getChange(key);
            log.info("「{}」发生变更，变更内容为：{}",key,configChange);
            Integer index = this.getIndex(key);
            String propertyName = this.getPropertyName(key);
            PropertyChange propertyChange = new PropertyChange();
            propertyChange.setPropertyName(propertyName);
            propertyChange.setOldValue(configChange.getOldValue());
            propertyChange.setNewValue(configChange.getNewValue());
            if (PropertyChangeType.ADDED.equals(configChange.getChangeType())){
                propertyChange.setChangeType(com.github.howinfun.sentinel.enums.PropertyChangeType.ADDED);
            }
            if (PropertyChangeType.MODIFIED.equals(configChange.getChangeType())){
                propertyChange.setChangeType(com.github.howinfun.sentinel.enums.PropertyChangeType.MODIFIED);
            }
            if (PropertyChangeType.DELETED.equals(configChange.getChangeType())){
                propertyChange.setChangeType(com.github.howinfun.sentinel.enums.PropertyChangeType.DELETED);
            }
            if (key.contains(FLOW)){
                addChange(index,propertyChange,flowRulesChangeMap);
            }
            if (key.contains(DEGRADE)){
                addChange(index,propertyChange,degradeRulesChangeMap);
            }
        });
    }

    /***
     * 添加属性变更
     * @author winfun
     * @param index index
     * @param change change
     * @param resultMap resultMap
     * @return {@link Void }
     **/
    private void addChange(Integer index,PropertyChange change,Map<Integer,List<PropertyChange>> resultMap){
        List<PropertyChange> changeList = resultMap.get(index);
        if (CollectionUtils.isEmpty(changeList)){
            changeList = new ArrayList<>();
            resultMap.put(index,changeList);
        }
        changeList.add(change);
    }

    /***
     * 获取索引
     * @author winfun
     * @param key key
     * @return {@link Integer }
     **/
    private Integer getIndex(String key){
        Integer left = key.indexOf("[");
        Integer index = Integer.parseInt(String.valueOf(key.charAt(left+1)));
        return index;
    }

    /***
     * 获取属性名
     * @author winfun
     * @param key key
     * @return {@link String }
     **/
    private String getPropertyName(String key){
        Integer right = key.indexOf("]");
        String property = key.substring(right+2);
        return property;
    }
    public static void main(String[] args) {
        String value = "sentinel.rules.flowRuleList[0].count";
        Integer left = value.indexOf("[");
        Integer right = value.indexOf("]");
        Integer index = Integer.parseInt(String.valueOf(value.charAt(left+1)));
        String property = value.substring(right+2);
        System.out.println(index);
        System.out.println(property);
    }
}
