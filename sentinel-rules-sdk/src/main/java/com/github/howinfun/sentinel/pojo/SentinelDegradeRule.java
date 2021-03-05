package com.github.howinfun.sentinel.pojo;

import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * Sentinel 熔断规则
 * @author winfun
 * @date 2021/3/4 6:21 下午
 **/
@Data
public class SentinelDegradeRule extends AbstractRule implements Serializable {

    private static final long serialVersionUID = -5687130403083095146L;
    /**
     * Circuit breaking strategy (0: average RT, 1: exception ratio, 2: exception count).
     * 熔断策略：平均响应时间、异常比例（时间窗口内异常数的比例）、异常数（时间窗口内达到一定的异常数）
     */
    private int grade = RuleConstant.DEGRADE_GRADE_RT;

    /**
     * Threshold count.
     * 阈值：
     *  平均响应时间：超过此阈值则为慢调用，然后搭配slowRatioThreshold使用，当慢调用比例超过slowRatioThreshold，则开启熔断
     *  异常比例：时间窗口内，异常数比例超过此阈值（0.1~1.0），则进行熔断
     *  异常数：时间窗口内，异常数达到此阈值，则进行熔断
     */
    private double count;

    /**
     * Recovery timeout (in seconds) when circuit breaker opens. After the timeout, the circuit breaker will
     * transform to half-open state for trying a few requests.
     * 时间窗口，单位为s
     */
    private int timeWindow;

    /**
     * Minimum number of requests (in an active statistic time span) that can trigger circuit breaking.
     * 触发断路的最小请求数
     * @since 1.7.0
     */
    private int minRequestAmount = RuleConstant.DEGRADE_DEFAULT_MIN_REQUEST_AMOUNT;

    /**
     * The threshold of slow request ratio in RT mode.
     * 慢请求比例阈值，适用在平均响应时间的熔断策略。
     */
    private double slowRatioThreshold = 1.0d;
    /**
     * 熔断时长，单位毫秒
     */
    private int statIntervalMs = 1000;
}
