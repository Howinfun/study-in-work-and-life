package com.hyf.testDemo.testReactor.singleThread;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/18
 */
public class AcceptorHandler implements Runnable{

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public AcceptorHandler(Selector selector,ServerSocketChannel serverSocketChannel){
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (null != socketChannel){
                new EchoServerHandler(selector,socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
