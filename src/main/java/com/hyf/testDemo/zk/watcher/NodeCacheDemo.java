package com.hyf.testDemo.zk.watcher;

import cn.hutool.core.thread.ThreadUtil;
import com.hyf.testDemo.zk.CuratorClientFacotry;
import com.hyf.testDemo.zk.crud.CURDDemo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/28
 */
@Slf4j
public class NodeCacheDemo {

    public static void main(String[] args) throws Exception{

        String path = "/nodecache";

        /*nodeCache(path);
        CURDDemo.create(path,"data");
        CURDDemo.update(path,"newData");
        CURDDemo.delete(path);*/


        /*pathChildrenCache(path);
        CURDDemo.create("/nodecache/child","child1");
        CURDDemo.update("/nodecache/child","child66");
        CURDDemo.delete("/nodecache/child");*/

        treeCache(path);
        CURDDemo.create("/nodecache/child1","child1");
        CURDDemo.create("/nodecache/child2","child2");
        CURDDemo.update("/nodecache/child1","child166");
        CURDDemo.delete("/nodecache/child1");


        ThreadUtil.sleep(Integer.MAX_VALUE);
    }

    private static void nodeCache(String path) throws Exception{
        try {
            CuratorFramework client = CuratorClientFacotry.newClient();
            client.start();
            /**
             * NodeCache 监听当前节点的增删改查事件。
             * ps：如果在监听的时候NodeCache监听的节点为空（也就是说ZNode路径不存在），也是可以的。之后，如果创建了对应的节点，也是会触发事件从而回调nodeChanged方法。
             */
            NodeCache nodeCache = new NodeCache(client,path);
            NodeCacheListener listener = new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData childData = nodeCache.getCurrentData();
                    log.info("节点 {} 状态改变，数据为：{}，Stat 为：{}",childData.getPath(),new String(childData.getData()),childData.getStat());
                }
            };
            nodeCache.getListenable().addListener(listener);
            //唯一的一个参数buildInitial代表着是否将该节点的数据立即进行缓存。如果设置为true的话，在start启动时立即调用NodeCache的getCurrentData方法就能够得到对应节点的信息ChildData类，如果设置为false，就得不到对应的信息。
            nodeCache.start();
        }catch (Exception e){
            log.error("NodeCache 监听异常：{}",e.getMessage());
        }
    }

    private static void pathChildrenCache(String path) throws Exception{
        try {
            CuratorFramework client = CuratorClientFacotry.newClient();
            client.start();
            /**
             * PathChildrenCache 监听当前节点的子节点增删改查事件
             *
             * 所有的PathChildrenCache构造方法的前三个参数都是一样的。
             *  · 第一个参数就是传入创建的Curator框架的客户端。
             *  · 第二个参数就是监听节点的路径。
             *  · 第三个重载参数cacheData表示是否把节点的内容缓存起来。如果cacheData为true，那么接收到节点列表变更事件的同时会将获得节点内容。(如果为 false，那么 data 一直是 null，一直获取不了)
             *
             * 除了上边的三个参数，其他参数的说明如下：
             *  · dataIsCompressed参数，表示是否对节点数据进行压缩。
             *  · threadFactory参数表示线程池工厂，当PathChildrenCache内部需要启动新的线程执行时，使用该线程池工厂来创建线程。
             *  · executorService和threadFactory参数差不多，表示通过传入的线程池或者线程工厂来异步处理监听事件。
             *
             * start方法可以传入启动的模式，定义在StartMode枚举中，具体如下：
             * · NORMAL——异步初始化cache
             * · BUILD_INITIAL_CACHE——同步初始化cache
             * · POST_INITIALIZED_EVENT——异步初始化cache，并触发完成事件
             *
             * StartMode枚举的三种启动方式，详细说明如下：（
             * （1）BUILD_INITIAL_CACHE模式：启动时同步初始化cache，表示创建cache后就从服务器提取对应的数据；
             * （2）POST_INITIALIZED_EVENT模式：启动时异步初始化cache，表示创建cache后从服务器提取对应的数据，完成后触发PathChildrenCacheEvent.Type#INITIALIZED事件，cache中Listener会收到该事件的通知；
             * （3）NORMAL模式：启动时异步初始化cache，完成后不会发出通知。
             */
            PathChildrenCache nodeCache = new PathChildrenCache(client,path,true);
            PathChildrenCacheListener listener = new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                    switch (pathChildrenCacheEvent.getType()){
                        case CHILD_ADDED:
                            log.info("子节点增加，path={}，data={}",pathChildrenCacheEvent.getData().getPath(),new String(pathChildrenCacheEvent.getData().getData()));
                            return;
                        case CHILD_REMOVED:
                            log.info("子节点删除，path={}，data={}",pathChildrenCacheEvent.getData().getPath(),new String(pathChildrenCacheEvent.getData().getData()));
                            return;
                        case CHILD_UPDATED:
                            log.info("子节点更新，path={}，data={}",pathChildrenCacheEvent.getData().getPath(),new String(pathChildrenCacheEvent.getData().getData()));
                            return;
                        default:
                            return;
                    }
                }
            };
            nodeCache.getListenable().addListener(listener);
            //唯一的一个参数buildInitial代表着是否将该节点的数据立即进行缓存。如果设置为true的话，在start启动时立即调用NodeCache的getCurrentData方法就能够得到对应节点的信息ChildData类，如果设置为false，就得不到对应的信息。
            nodeCache.start();
        }catch (Exception e){
            log.error("NodeCache 监听异常：{}",e.getMessage());
        }
    }

    private static void treeCache(String path){

        try {
            CuratorFramework client = CuratorClientFacotry.newClient();
            client.start();
            /**
             * Tree Cache可以看作是Node Cache和Path Cache的合体。Tree Cache不光能监听子节点，还能监听节点自身。
             *
             * 第一个参数就是传入创建的Curator框架的客户端，第二个参数就是监听节点的路径，其他参数的简单说明如下：
             * · dataIsCompressed：表示是否对数据进行压缩。
             * · maxDepth表示缓存的层次深度，默认为整数最大值。
             * · executorService表示监听的执行线程池，默认会创建一个单一线程的线程池。
             * · createParentNodes表示是否创建父亲节点，默认为false。
             */
            TreeCache treeCache = new TreeCache(client,path);
            TreeCacheListener listener = new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                    switch (treeCacheEvent.getType()){
                        case NODE_ADDED:
                            log.info("节点增加，path={}，data={}",treeCacheEvent.getData().getPath(),new String(treeCacheEvent.getData().getData()));
                            return;
                        case NODE_UPDATED:
                            log.info("子节点删除，path={}，data={}",treeCacheEvent.getData().getPath(),new String(treeCacheEvent.getData().getData()));
                            return;
                        case NODE_REMOVED:
                            log.info("子节点更新，path={}，data={}",treeCacheEvent.getData().getPath(),new String(treeCacheEvent.getData().getData()));
                            return;
                    }
                }
            };
            treeCache.getListenable().addListener(listener);
            treeCache.start();
        }catch (Exception e){
            log.error("TreeCache 监听异常：{}",e.getMessage());
        }
    }
}
