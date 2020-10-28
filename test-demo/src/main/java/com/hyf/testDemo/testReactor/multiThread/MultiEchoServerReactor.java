package com.hyf.testDemo.testReactor.multiThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/4
 */
public class MultiEchoServerReactor{

    public static SubReactor[] subReactors;
    public static Selector[] selectors;
    public static AtomicInteger next = new AtomicInteger(0);

    public MultiEchoServerReactor(int port) throws IOException {

        Selector selector0 = Selector.open();
        Selector selector1 = Selector.open();
        selectors = new Selector[]{selector0,selector1};

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        SelectionKey sk = serverSocketChannel.register(selector0,0);
        sk.attach(new MultiAcceptorHandler(serverSocketChannel));
        sk.interestOps(SelectionKey.OP_ACCEPT);

        SubReactor subReactor0 = new SubReactor(selector0);
        SubReactor subReactor1 = new SubReactor(selector1);
        subReactors = new SubReactor[]{subReactor0,subReactor1};
    }

    public void startService(){
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    public static void main(String[] args) throws IOException {
        MultiEchoServerReactor reactor = new MultiEchoServerReactor(8080);
        reactor.startService();
    }
}
