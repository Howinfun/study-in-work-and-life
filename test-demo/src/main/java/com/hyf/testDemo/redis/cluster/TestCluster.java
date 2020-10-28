package com.hyf.testDemo.redis.cluster;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试Redis集群
 * @author winfun
 * @date 2020/10/21 5:48 下午
 **/
public class TestCluster {

    public static void main(String[] args) throws Exception{

        Set<HostAndPort> nodes = new HashSet<>(6);
        nodes.add(new HostAndPort("127.0.0.1",6379));
        nodes.add(new HostAndPort("127.0.0.1",6380));
        nodes.add(new HostAndPort("127.0.0.1",6381));

        JedisCluster cluster = new JedisCluster(nodes);
        String value = cluster.get("key");
        System.out.println("get: key is key,value is "+value);
        String result = cluster.set("key2","hello world");
        System.out.println("set: key is key2,result is "+result);
        cluster.close();
    }
}
