package com.hyf.testDemo.testReactor.singleThread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/4
 */
public class EchoClientHandler implements Runnable{

    final SocketChannel socketChannel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public EchoClientHandler(SelectionKey sk, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.sk = sk;
    }

    @Override
    public void run() {
        try {
            if (sk.isReadable()){
                int length = 0;
                // 从通道读取到 byteBuffer
                while ((length = socketChannel.read(byteBuffer)) > 0){
                    System.out.println("服务端接收到客户端的信息：" + new String(byteBuffer.array(),0,length));
                }
                // 重新进入写模式
                byteBuffer.clear();
            }else if (sk.isWritable()){
                socketChannel.write(EchoClient.byteBuffer);
                // byteBuffer 转为写模式
                EchoClient.byteBuffer.clear();
                // 注册 read 就绪模式
                sk.interestOps(SelectionKey.OP_READ);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
