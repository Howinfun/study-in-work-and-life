package com.hyf.testDemo.zk.idmaker;

import com.hyf.testDemo.zk.CuratorClientFacotry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/28
 */
@Slf4j
public class IDMaker {

    private static  CuratorFramework client = CuratorClientFacotry.newClient();
    static {
        client.start();
    }
    private static String createSeqNode(String rootPath){

        try {

            Stat stat = client.checkExists().forPath(rootPath);
            if (null == stat){
                // 根节点是持久顺序的，它会为它的第一级子节点维护一份顺序编号，会记录每个子节点创建的先后顺序，这个顺序编号是分布式同步的，也是全局唯一的。
                // 在创建子节点时，如果设置为上面的类型，ZooKeeper会自动为创建后的节点路径在末尾加上一个数字，用来表示顺序。这个顺序值的最大上限就是整型的最大值。
                client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(rootPath);
            }
            String IDPath = rootPath + "/ID-";
            // 创建临时顺序节点，在 Client 断开连接后，临时节点不会立刻消失，而是10s后再被删除
            String destPath = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(IDPath);
            return destPath;
        } catch (Exception e) {
            log.error("创建ID失败，创建时节点为：{}，异常：{}",rootPath,e);
            return null;
        }
    }

    public static String makeId(String nodeName){

        String rootPath = "/"+nodeName;
        String destPath = createSeqNode(rootPath);
        if (null == destPath){
            return null;
        }
        try {
            // 成功创建ID后，将临时顺序节点删除，避免浪费内存资源
            client.delete().forPath(destPath);
        } catch (Exception e) {
            log.error("创建ID后，删除临时节点失败！临时顺序节点为：{}"+destPath);
        }
        int index  = destPath.indexOf(nodeName);
        if (index >= 0){
            index += nodeName.length();
            return index <= destPath.length() ? destPath.substring(index) : "";
        }
        return destPath;
    }
}
