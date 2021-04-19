package com.hyf.testDemo.redis.zujian;

import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2020/4/2
 */
@Data
public class Funnel {

    /** 漏斗总容量 */
    private int capacity;
    /** 漏嘴流水速率 */
    private float leakingRate;
    /** 漏斗剩余空间 */
    private Integer leftQuota;
    /** 上一次漏水时间 */
    private long leakingTs;

    public Funnel(int capacity,float leakingRate){
        this.capacity = capacity;
        this.leakingRate = leakingRate;
        this.leftQuota = capacity;
        this.leakingTs = System.currentTimeMillis();
    }

    public void makeSpace(){
        // 当前时间
        long nowTs = System.currentTimeMillis();
        // 距离上次漏水时间的时间间隔
        long timeInterval = nowTs - leakingTs;
        // 时间间隔里 腾出的容量
        int addCapacity = (int) (timeInterval * leakingRate / 1000);
        // 时间间隔太长，会导致 腾出的容量 溢出为负数
        if (addCapacity < 0){
            return;
        }
        // 时间间隔太短，腾出空间太小不足为1，但是最小单位是1
        if (addCapacity < 1){
            return;
        }
        leftQuota += addCapacity;
        leakingTs = nowTs;
        if (leftQuota > capacity){
            leftQuota = capacity;
        }
    }

    public boolean watering(int quota){
        // 每次漏水前，先重新计算剩余容量
        makeSpace();
        if (leftQuota >= quota){
            // 扣除掉用了的容量
            leftQuota -= quota;
            return true;
        }
        return false;
    }
}
