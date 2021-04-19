package com.hyf.testDemo.zk.watcher;

import cn.hutool.core.thread.ThreadUtil;
import com.hyf.testDemo.zk.CuratorClientFacotry;
import com.hyf.testDemo.zk.crud.CURDDemo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author Howinfun
 * @desc Watcher 机制，监听是一次性消费的，如果需要一直监听则需要反复注册 Watcher。
 * deleteBuilder、createBuilder 和 setDataBuilder 都没有 watcher 机制。
 * @date 2020/8/28
 */
@Slf4j
public class WatcherDemo {
    private static CuratorFramework client = CuratorClientFacotry.newClient();
    static {
        client.start();
    }

    public static void main(String[] args) throws Exception{

        String path = "/watcher";
        /*boolean flag = existWatcher(path);
        if (flag){
            log.info("节点 {} 存在！",path);
        }else {
            log.info("节点 {} 不存在！",path);
        }
        //CURDDemo.create(path,"watcher");
        //CURDDemo.update(path,"watcher2");
        CURDDemo.delete(path);*/

        /*String data = getDataWatcher(path);
        log.info("节点 {} 数据为：{}",path,data);
        //CURDDemo.update(path,"watcher2");
        //CURDDemo.delete(path);*/

        List<String> childs = childWatcher(path);
        log.info("节点 {} 存在 {} 个节点",path,childs == null ? 0 : childs.size());
        CURDDemo.create(path+"/child1","watcherChild1");
        CURDDemo.update(path+"/child1","watcherChild166");
        CURDDemo.delete(path+"/child1");
        ThreadUtil.sleep(Integer.MAX_VALUE);
    }

    /**
     * GetData Watcher 监听机制，监听本节点的删改事件。
     * @param path
     * @return
     */
    private static String getDataWatcher(String path){

        try {
            Stat stat = client.checkExists().forPath(path);
            if (null == stat){
                log.info("{} 节点不存在！",path);
                return null;
            }else {
                byte[] data = client.getData().usingWatcher(new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        switch (watchedEvent.getType().getIntValue()){
                            case 0:
                            default:
                                throw new RuntimeException("Invalid integer value for conversion to EventType");
                            case 1:
                                log.info("节点 {} 被创建！",watchedEvent.getPath());
                                return;
                            case 2:
                                log.info("节点 {} 被删除！",watchedEvent.getPath());
                                return;
                            case 3:
                                log.info("节点 {} 数据被修改！",watchedEvent.getPath());
                                return;
                            case 4:
                                log.info("节点 {} 子节点被修改（增删，不包括子节点数据修改）！",watchedEvent.getPath());
                                return;
                        }
                    }
                }).forPath(path);
                return new String(data);
            }
        }catch (Exception e){
            log.error("GetDataWatcher 异常！");
            return null;
        }
    }

    /**
     * Exist Watcher 监听机制，监听本节点的增删改事件。
     * @param path
     * @return
     */
    private static boolean existWatcher(String path){

        try {
            Stat stat = client.checkExists().usingWatcher(new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    switch (watchedEvent.getType().getIntValue()){
                        case 0:
                        default:
                            throw new RuntimeException("Invalid integer value for conversion to EventType");
                        case 1:
                            log.info("节点 {} 被创建！",watchedEvent.getPath());
                            return;
                        case 2:
                            log.info("节点 {} 被删除！",watchedEvent.getPath());
                            return;
                        case 3:
                            log.info("节点 {} 数据被修改！",watchedEvent.getPath());
                            return;
                        case 4:
                            log.info("节点 {} 子节点被修改（增删，不包括子节点数据修改）！",watchedEvent.getPath());
                            return;
                    }
                }
            }).forPath(path);
            if (null == stat){
                return false;
            }
        }catch (Exception e){
            log.error("ExistWatcher 异常！");
            return false;
        }
        return true;
    }

    /**
     * Child Watcher 监听机制，监听子节点的增删事件。
     * @param path
     * @return
     */
    private static List<String> childWatcher(String path){

        try {
            Stat stat = client.checkExists().forPath(path);
            if (null == stat){
                log.info("{} 节点不存在！",path);
                return null;
            }else {
                List<String> childs = client.getChildren().usingWatcher(new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        switch (watchedEvent.getType().getIntValue()){
                            case 0:
                            default:
                                throw new RuntimeException("Invalid integer value for conversion to EventType");
                            case 1:
                                log.info("节点 {} 被创建！",watchedEvent.getPath());
                                return;
                            case 2:
                                log.info("节点 {} 被删除！",watchedEvent.getPath());
                                return;
                            case 3:
                                log.info("节点 {} 数据被修改！",watchedEvent.getPath());
                                return;
                            case 4:
                                log.info("节点 {} 子节点被修改（增删，不包括子节点数据修改）！",watchedEvent.getPath());
                                return;
                        }
                    }
                }).forPath(path);
                return childs;
            }
        }catch (Exception e){
            log.error("ChildWatcher 异常！");
            return null;
        }
    }
}
