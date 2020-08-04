package com.hyf.testDemo.testReactor.singleThread;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/18
 */
public class EchoServerReactor implements Runnable{

    final private Selector selector;
    final private ServerSocketChannel serverSocketChannel;

    public EchoServerReactor(int port) throws Exception{

        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口号
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // 非阻塞
        serverSocketChannel.configureBlocking(false);
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new AcceptorHandler(selector,serverSocketChannel));

    }

    @Override
    public void run() {
        try {
            System.out.println("我是 EchoServerReactor，我启动了");
            Set selected;
            while (true){
                if (selector.select() > 0){
                    selected = selector.selectedKeys();
                    Iterator it = selected.iterator();
                    while (it.hasNext()){
                        SelectionKey selectionKey = (SelectionKey) it.next();
                        // 根据事件分发到对应 Handler 进行处理
                        dispatch(selectionKey);
                    }
                    selected.clear();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 分发
     * @param selectionKey
     */
    private void dispatch(SelectionKey selectionKey){
        Runnable handler = (Runnable) selectionKey.attachment();
        handler.run();
    }

    public static void main(String[] args) throws Exception{

        EchoServerReactor echoServerReactor = new EchoServerReactor(8080);
        Thread thread = new Thread(echoServerReactor);
        thread.start();

    }
}
