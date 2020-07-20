package com.hyf.testDemo.testReactor;

import cn.hutool.core.thread.ThreadUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/18
 */
public class WriteHandler {

    public static void handle(SelectionKey selectionKey){
        try {

            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            String msg = new Date() + "Hello Client~";
            byteBuffer.put(msg.getBytes());
            socketChannel.write(byteBuffer);
            ThreadUtil.sleep(2000);

        } catch (Exception e){
            System.out.println("ReadHandler 处理失败！");
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
