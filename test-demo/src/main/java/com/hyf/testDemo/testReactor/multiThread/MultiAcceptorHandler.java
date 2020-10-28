package com.hyf.testDemo.testReactor.multiThread;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/18
 */
public class MultiAcceptorHandler implements Runnable{

    private final ServerSocketChannel serverSocketChannel;

    public MultiAcceptorHandler(ServerSocketChannel serverSocketChannel){
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (null != socketChannel){
                Selector selector = MultiEchoServerReactor.selectors[MultiEchoServerReactor.next.get()];
                new MultiEchoServerHandler(selector,socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (MultiEchoServerReactor.next.getAndIncrement() == MultiEchoServerReactor.selectors.length){
            MultiEchoServerReactor.next.set(0);
        }
    }
}
