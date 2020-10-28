package com.hyf.testDemo.testReactor.singleThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/4
 */
public class EchoClient implements Runnable {

    static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    final Selector selector;

    public EchoClient (Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            System.out.println("我是 EchoClient，我启动了");
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

    private void dispatch(SelectionKey selectionKey){
        Runnable handler = (Runnable) selectionKey.attachment();
        handler.run();
    }

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",8080));
        socketChannel.configureBlocking(false);
        // 往 selector 注册，取得选择键，此时暂不设置 IO 事件
        SelectionKey sk = socketChannel.register(selector,0);
        sk.attach(new EchoClientHandler(sk,socketChannel));
        new Thread(new EchoClient(selector)).start();

        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入：");
            if (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                System.out.println(msg);
                byteBuffer.put(msg.getBytes());
                // byteBuffer 转为读模式
                byteBuffer.flip();
                sk.interestOps(SelectionKey.OP_WRITE);
                selector.wakeup();
            }
        }
    }
}
