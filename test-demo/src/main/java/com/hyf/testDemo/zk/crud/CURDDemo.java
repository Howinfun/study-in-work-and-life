package com.hyf.testDemo.zk.crud;

import com.hyf.testDemo.zk.CuratorClientFacotry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/27
 */
@Slf4j
public class CURDDemo {

    public static CuratorFramework client = CuratorClientFacotry.newClient();
    static {
        client.start();
    }

    public static void main(String[] args) throws Exception{

        //create("/test/child4","child4");
        //delete("/test/child3");
        //("/test/child1","child1child1");
        getChildren("/test");
        client.close();
    }

    public static void create(String path,String data) throws Exception{

        Stat stat = client.checkExists().forPath(path);
        if (null == stat){
            String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,data.getBytes());
            log.info("成功创建节点：{}",result);
        }else {
            log.info("节点 {} 已存在！",path);
        }
    }

    public static void update(String path,String data) throws Exception{
        Stat stat = client.checkExists().forPath(path);
        if (null == stat){
            log.info("{} 节点不存在！",path);
            return;
        }
        byte[] oldDataArr = client.getData().forPath(path);
        String oldData = new String(oldDataArr);
        client.setData().forPath(path,data.getBytes());
        log.info("{} 节点更新数据成功：更新前数据为 {}，更新后数据为 {}",path,oldData,new String(data));
    }

    public static String getData(String path) throws Exception{

        Stat stat = client.checkExists().forPath(path);
        if (null == stat){
            log.info("{} 节点不存在！",path);
            return null;
        }else {
            return new String(client.getData().forPath(path));
        }
    }

    public static void delete(String path) throws Exception{

        Stat stat = client.checkExists().forPath(path);
        if (null == stat){
            log.info("{} 节点不存在！",path);
            return;
        }
        List<String> childList = client.getChildren().forPath(path);
        if (childList.isEmpty()){
            client.delete().forPath(path);
            log.info("成功删除节点：{}",path);
        }else {
            log.info("{} 节点存在子节点，不能删除！",path);
        }
    }

    public static void getChildren(String path) throws Exception{

        Stat stat = client.checkExists().forPath(path);
        if (null == stat){
            log.info("{} 节点不存在！",path);
            return;
        }
        List<String> childList = client.getChildren().forPath(path);
        if (childList.isEmpty()){
            log.info("{} 节点不存在子节点！",path);
        }else {
            // child 是子节点的名称，不是全路径
            childList.forEach(child -> {
                String childPath = path + "/" + child;
                String data  = null;
                try {
                    data = getData(childPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("子节点 {} 的数据为：{}",childPath,data);
            });

        }
    }
}
