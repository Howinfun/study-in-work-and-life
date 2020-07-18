package com.hyf.testDemo.testNIO;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/18
 */
public class AcceptorHandler {

    public static void handle(SelectionKey selectionKey){
        try {

            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            // 要设置非阻塞，才能往 selector 注册
            socketChannel.configureBlocking(false);
            // 注册所有状态
            socketChannel.register(selectionKey.selector(), socketChannel.validOps());

        } catch (Exception e){
            System.out.println("AcceptorHandler 处理失败！");
            e.printStackTrace();
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
