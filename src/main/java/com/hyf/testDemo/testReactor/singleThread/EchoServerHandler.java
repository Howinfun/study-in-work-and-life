package com.hyf.testDemo.testReactor.singleThread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/4
 */
public class EchoServerHandler implements Runnable{

    final SocketChannel socketChannel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int RECEIVING = 0,SENDING = 1;
    int state = RECEIVING;

    public EchoServerHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        // 往 selector 注册，取得选择键，此时暂不设置 IO 事件
        sk = this.socketChannel.register(selector,0);
        // Handler 将自身实例注册到选择键中。
        sk.attach(this);
        // 注册 Read 就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        // 唤醒 selector
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (sk.isWritable()){
                // 将 byteBuffer 写入通道
                socketChannel.write(byteBuffer);
                // 切换成写模式
                byteBuffer.clear();
                // 注册 read 就绪模式
                sk.interestOps(SelectionKey.OP_READ);
                // 改为接收状态
                state = RECEIVING;
            }else if (sk.isReadable()){
                int length;
                // 从通道读取到 byteBuffer
                while ((length = socketChannel.read(byteBuffer))>0){
                    System.out.println("服务端接收到客户端的信息：" + new String(byteBuffer.array(),0,length));
                }
                // 进入读模式
                byteBuffer.flip();
                // 注册 write 就绪模式
                sk.interestOps(SelectionKey.OP_WRITE);
                // 改为接发送状态
                state = SENDING;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
