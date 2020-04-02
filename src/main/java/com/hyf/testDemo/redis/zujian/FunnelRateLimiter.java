package com.hyf.testDemo.redis.zujian;

import cn.hutool.core.thread.ThreadUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2020/4/2
 */
public class FunnelRateLimiter {

    private Map<String,Funnel> funnels = new HashMap<>(20);

    public boolean isAcationAllowed(String userId, String actionKey, int capacity, float leakingRate){
        String key = String.format("%s:%s", userId, actionKey);
        Funnel funnel = funnels.get(key);
        if (funnel == null) {
            funnel = new Funnel(capacity, leakingRate);
            funnels.put(key, funnel);
        }
        // 需要1个quota（每次一个请求）
        return funnel.watering(1);
    }

    public static void main(String[] args) {

        FunnelRateLimiter limiter = new FunnelRateLimiter();
        for (int i = 0;i<=1000000;i++){
            ThreadUtil.sleep(250);
            // 漏斗容量为20，假设每60秒只允许30个操作，则漏水速率为 0.5f
            System.out.println(limiter.isAcationAllowed("123","query",20,0.5f));
        }
    }
}
