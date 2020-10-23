package com.hyf.testDemo.redis.zujian;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Howinfun
 * @desc
 * @date 2020/4/7
 */
public class RedisPool {

    private static final JedisPool jedisPool;

    static {
        GenericObjectPoolConfig poolConfig =new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(6);
        poolConfig.setMaxIdle(3);
        poolConfig.setMinIdle(1);
        jedisPool = new JedisPool(poolConfig,"127.0.0.1", 6371, 10000, null, 15);
    }

    public void execute(CallWithJedis caller){
        try (Jedis jedis = jedisPool.getResource()){
            caller.call(jedis);
        }
        // 连接重试
        /*Jedis jedis = jedisPool.getResource();
        try {
            caller.call(jedis);
        } catch (JedisConnectionException e) {
            caller.call(jedis);
        } finally {
            jedis.close();
        }*/
    }

    public static void main(String[] args) {
        RedisPool pool = new RedisPool();
        HolderValue<String> value = new HolderValue<>();
        pool.execute(jedis -> {
            jedis.set("hello","world");
            value.setValue(jedis.get("hello"));
        });
        System.out.println(value);
    }

}
