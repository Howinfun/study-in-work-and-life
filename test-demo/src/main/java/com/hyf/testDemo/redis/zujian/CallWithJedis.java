package com.hyf.testDemo.redis.zujian;

import redis.clients.jedis.Jedis;

/**
 * @author Howinfun
 * @desc
 * @date 2020/4/7
 */
public interface CallWithJedis {

    void call(Jedis jedis);
}
