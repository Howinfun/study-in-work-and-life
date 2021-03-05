package com.github.howinfun.sentinel.mapping;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.github.howinfun.sentinel.pojo.SentinelDegradeRule;
import com.github.howinfun.sentinel.pojo.SentinelFlowRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * Sentinel Rules Mapping
 * @author winfun
 * @date 2021/3/5 2:24 下午
 **/
@Mapper
public interface SentinelRulesMapping {

    SentinelRulesMapping INSTANCE = Mappers.getMapper(SentinelRulesMapping.class);

    /**
     * to Sentinel FlowRule
     * @param sentinelFlowRule
     * @return
     */
    @Mappings({
            @Mapping(target = "grade",source = "grade"),
            @Mapping(target = "count",source = "count"),
            @Mapping(target = "strategy",source = "strategy"),
            @Mapping(target = "refResource",source = "refResource"),
            @Mapping(target = "controlBehavior",source = "controlBehavior"),
            @Mapping(target = "warmUpPeriodSec",source = "warmUpPeriodSec"),
            @Mapping(target = "maxQueueingTimeMs",source = "maxQueueingTimeMs"),
            @Mapping(target = "clusterMode",source = "clusterMode")
    })
    FlowRule toFlowRule(SentinelFlowRule sentinelFlowRule);

    /**
     * to Sentinel DegradeRule
     * @param sentinelDegradeRule
     * @return
     */
    @Mappings({
            @Mapping(target = "grade",source = "grade"),
            @Mapping(target = "count",source = "count"),
            @Mapping(target = "timeWindow",source = "strategy"),
            @Mapping(target = "minRequestAmount",source = "refResource"),
            @Mapping(target = "slowRatioThreshold",source = "controlBehavior"),
            @Mapping(target = "statIntervalMs",source = "warmUpPeriodSec")
    })
    DegradeRule toDegradeRule(SentinelDegradeRule sentinelDegradeRule);
}
