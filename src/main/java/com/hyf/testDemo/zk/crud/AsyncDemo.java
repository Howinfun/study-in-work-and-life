package com.hyf.testDemo.zk.crud;

import cn.hutool.core.thread.ThreadUtil;
import com.hyf.testDemo.zk.CuratorClientFacotry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/27
 */
@Slf4j
public class AsyncDemo {

    public static CuratorFramework client = CuratorClientFacotry.newClient();

    public static void main(String[] args) throws Exception{


        client.start();

        updateAsync("/test/child7","7776");
        log.info("你先创建，我去上个厕所~");
        ThreadUtil.sleep(Integer.MAX_VALUE);
        CloseableUtils.closeQuietly(client);
    }

    public static void createAsync(String path, String data) throws Exception{

        Stat stat = client.checkExists().forPath(path);
        if (null == stat){
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground((curatorFramework, curatorEvent) -> log.info("节点 {} 创建成功！curatorEvent：{}",path,curatorEvent)).forPath(path,data.getBytes());
        }else {
            log.info("节点 {} 已存在！",path);
        }
    }

    public static void updateAsync(String path,String data) throws Exception{
        Stat stat = client.checkExists().forPath(path);
        if (null == stat){
            log.info("{} 节点不存在！",path);
            return;
        }
        byte[] oldDataArr = client.getData().forPath(path);
        String oldData = new String(oldDataArr);
        client.setData().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                log.info(Thread.currentThread().getName());
                log.info("节点 {} 更新成功！curatorEvent：{}",path,curatorEvent);
            }
        }).forPath(path,data.getBytes());
    }
}
