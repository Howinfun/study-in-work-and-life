package com.hyf.testDemo.testNIO;

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
public class Reactor implements Runnable{

    final private Selector selector;
    final private ServerSocketChannel serverSocket;

    public Reactor(int port) throws Exception{

        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        // 绑定端口号
        serverSocket.socket().bind(new InetSocketAddress(port));
        // 非阻塞
        serverSocket.configureBlocking(false);
        SelectionKey selectionKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);

    }

    @Override
    public void run() {
        try {
            System.out.println("我是 Reactor，我启动了");
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
        if (selectionKey.isAcceptable()){
            AcceptorHandler.handle(selectionKey);
        } else if (selectionKey.isReadable()){
            ReadHandler.handle(selectionKey);
        }else if (selectionKey.isWritable()){
            WriteHandler.handle(selectionKey);
        }
    }

    public static void main(String[] args) throws Exception{


        Reactor reactor = new Reactor(8080);
        Thread thread = new Thread(reactor);
        thread.start();

    }
}
