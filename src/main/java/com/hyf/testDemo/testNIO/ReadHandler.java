package com.hyf.testDemo.testNIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/18
 */
public class ReadHandler {

    public static void handle(SelectionKey selectionKey){
        try {

            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // (3) 面向 Buffer
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println("服务端接收到客户端的信息：" + Charset.defaultCharset().newDecoder().decode(byteBuffer)
                    .toString());

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
