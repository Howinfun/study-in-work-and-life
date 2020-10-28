package com.hyf.testDemo.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/27
 */
public class CuratorClientFacotry {

    private static String connectStr = "127.0.0.1:2181";

    public static CuratorFramework newClient() {
        // 重试 第一个参数：休眠时间（毫秒） 第二个参数：最大重试次数
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(2000,3);
        CuratorFramework framework = CuratorFrameworkFactory.newClient(connectStr,retry);
        return framework;
    }
}
