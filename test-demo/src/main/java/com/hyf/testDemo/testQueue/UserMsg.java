package com.hyf.testDemo.testQueue;

import lombok.Data;
import lombok.ToString;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/21
 */
@Data
@ToString
public class UserMsg implements Delayed {

    private int id;
    private String phone;
    private String msg;
    private int failCount;
    // 过期时间
    private long time;

    public UserMsg(int id,String phone,String msg,long time,TimeUnit unit){
        this.id = id;
        this.phone = phone;
        this.msg = msg;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
        this.failCount = 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return this.time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        UserMsg item = (UserMsg) o;
        long diff = this.time - item.time;
        if (diff <= 0) {
            return -1;
        }else {
            return 1;
        }
    }
}
