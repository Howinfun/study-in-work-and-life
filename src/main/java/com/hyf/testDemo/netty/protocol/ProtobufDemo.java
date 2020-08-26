package com.hyf.testDemo.netty.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/26
 */
public class ProtobufDemo {

    public static void main(String[] args) throws Exception{
        JsonMsgProto.Msg.Builder builder = JsonMsgProto.Msg.newBuilder();
        builder.setId(1);
        builder.setContent("你好，世界！");
        JsonMsgProto.Msg msg = builder.build();

        // 序列化/反序列化

        /**
         * 第一种方式
         * 这种方式类似于普通Java对象的序列化，适用于很多将Protobuf的POJO序列化到内存或者外层的应用场景
         */
        byte[] data = msg.toByteArray();
        System.out.println(data);
        // 可用于网络传输，保存到内存或外存
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(data);
        data = outputStream.toByteArray();
        JsonMsgProto.Msg msg1 = JsonMsgProto.Msg.parseFrom(data);
        System.out.println("id："+msg1.getId()+"，content："+msg1.getContent());

        /**
         * 第二种方式
         * 使用 ByteArrayInputStream
         * 会出现半包问题（粘包和半包）
         */
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        JsonMsgProto.Msg msg2 = JsonMsgProto.Msg.parseFrom(inputStream);
        System.out.println("id："+msg2.getId()+"，content："+msg2.getContent());

        /**
         * 第三种方式
         * 使用带 Delimited 的方法解决半包问题
         */
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        msg.writeDelimitedTo(outputStream2);
        ByteArrayInputStream inputStream2 = new ByteArrayInputStream(outputStream2.toByteArray());
        JsonMsgProto.Msg msg3 = JsonMsgProto.Msg.parseDelimitedFrom(inputStream2);
        System.out.println("id："+msg3.getId()+"，content："+msg3.getContent());
    }
}
