package com.github.howinfun.sentinel.pojo;

import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * Sentinel 流控规则 - com.alibaba.csp.sentinel.slots.block.flow.FlowRule
 * @author winfun
 * @date 2021/3/4 6:18 下午
 **/
@Data
public class SentinelFlowRule extends AbstractRule implements Serializable {

    private static final long serialVersionUID = -6301427067089440279L;

    /**
     * The threshold type of flow control (0: thread count, 1: QPS).
     * 参考：
     *      public static final int FLOW_GRADE_THREAD = 0;
     *      public static final int FLOW_GRADE_QPS = 1;
     */
    private Integer grade = RuleConstant.FLOW_GRADE_QPS;;

    /**
     * Flow control threshold count.
     */
    private double count;

    /**
     * Flow control strategy based on invocation chain.
     *
     * {@link RuleConstant#STRATEGY_DIRECT} for direct flow control (by origin);
     * {@link RuleConstant#STRATEGY_RELATE} for relevant flow control (with relevant resource);
     * {@link RuleConstant#STRATEGY_CHAIN} for chain flow control (by entrance resource).
     * 流控策略：直接、关联、链路
     */
    private int strategy = RuleConstant.STRATEGY_DIRECT;

    /**
     * Reference resource in flow control with relevant resource or context.
     * 关联Resource，关联策略有效
     */
    private String refResource;

    /**
     * Rate limiter control behavior.
     * 0. default(reject directly), 1. warm up, 2. rate limiter, 3. warm up + rate limiter
     * 流控效果：直接拒绝（快速失败）、预热启动（令牌桶算法）、排队等待（漏桶算法）、预热+排队等待
     */
    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;

    /**
     * 预热时长
     * 公式一：stableInterval  stableInterval = 1/count  其中 count 为 qps 阈值
     * 公式二：coldInterval    coldInterval = stableInterval * coldFactor 其中 coldFator 默认为3，可看 SentinelConfig.DEFAULT_COLD_FACTOR
     */
    private int warmUpPeriodSec = 10;

    /**
     * Max queueing time in rate limiter behavior.
     * 针对排队等待的流控效果：最大排队等待时间，如果当前请求计算的等待时间大于此值，则不等待，直接返回false。
     *
     */
    private int maxQueueingTimeMs = 500;

    private boolean clusterMode;
    /**
     * Flow rule config for cluster mode.
     * private ClusterFlowConfig clusterConfig;
     */

    /**
     * The traffic shaping (throttling) controller.
     * private TrafficShapingController controller;
     */

}
