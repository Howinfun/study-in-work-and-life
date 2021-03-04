package com.github.howinfun.sentinel.pojo;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import com.alibaba.csp.sentinel.slots.block.flow.TrafficShapingController;
import lombok.Data;

/**
 * Sentinel 流控规则
 * @author winfun
 * @date 2021/3/4 6:18 下午
 **/
@Data
public class SentinelFlowRule {

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
     */
    private int strategy = RuleConstant.STRATEGY_DIRECT;

    /**
     * Reference resource in flow control with relevant resource or context.
     */
    private String refResource;

    /**
     * Rate limiter control behavior.
     * 0. default(reject directly), 1. warm up, 2. rate limiter, 3. warm up + rate limiter
     * 流控效果：直接拒绝（快速失败）、预热启动（令牌桶算法）、排队等待（漏桶算法）、预热+排队等待
     */
    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;

    private int warmUpPeriodSec = 10;

    /**
     * Max queueing time in rate limiter behavior.
     */
    private int maxQueueingTimeMs = 500;

    private boolean clusterMode;
    /**
     * Flow rule config for cluster mode.
     */
    private ClusterFlowConfig clusterConfig;

    /**
     * The traffic shaping (throttling) controller.
     */
    private TrafficShapingController controller;

}
