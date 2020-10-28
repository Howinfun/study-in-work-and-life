package com.hyf.testDemo.zk.lock;

import cn.hutool.core.thread.ThreadUtil;
import com.hyf.testDemo.zk.CuratorClientFacotry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Howinfun
 * @desc
 * @date 2020/9/1
 */
@Slf4j
public class ZKLockTest {

    private static int count = 0;

    public static void main(String[] args) {
        CuratorFramework client = CuratorClientFacotry.newClient();
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client,"/zklock");

        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.execute(()->{
                try {
                    lock.acquire();
                    for (int j = 0; j < 10; j++) {
                        count++;
                    }
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    log.info("count 的值："+count);
                }
            });
        }

        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
