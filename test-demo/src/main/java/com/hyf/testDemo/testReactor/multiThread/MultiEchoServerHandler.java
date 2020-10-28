package com.hyf.testDemo.testReactor.multiThread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/4
 */
public class MultiEchoServerHandler implements Runnable{

    final SocketChannel socketChannel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    public static ExecutorService pool = Executors.newFixedThreadPool(4);

    public MultiEchoServerHandler(Selector selector, SocketChannel socketChannel) throws IOException {
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
        System.out.println("当期线程："+Thread.currentThread().getName());
        pool.execute(()->this.asynRun());
    }

    public void asynRun(){
        try {
            System.out.println("当期线程："+Thread.currentThread().getName());
            if (sk.isWritable()){
                // 将 byteBuffer 写入通道
                socketChannel.write(byteBuffer);
                // 切换成写模式
                byteBuffer.clear();
                // 注册 read 就绪模式
                sk.interestOps(SelectionKey.OP_READ);
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
