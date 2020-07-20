package com.hyf.testDemo.testReactor;

import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/18
 */
public class Client {

    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("127.0.0.1",8080);
        new Thread(()->{
            try {
                while (true){
                    socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
            }
        }).start();

        new Thread(()->{
            try {
                while (true){
                    byte[] bytes = new byte[1024];
                    socket.getInputStream().read(bytes);
                    if (bytes.length > 0){
                        System.out.println("结束到服务端的信息："+new String(bytes, Charset.forName("GBK")));
                    }
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
            }
        }).start();
    }
}
