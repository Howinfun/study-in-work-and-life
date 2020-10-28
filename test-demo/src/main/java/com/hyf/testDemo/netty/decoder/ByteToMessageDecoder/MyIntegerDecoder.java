package com.hyf.testDemo.netty.decoder.ByteToMessageDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/21
 */
public class MyIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() >= 4){
            int num = byteBuf.readInt();
            System.out.println("解码出一个整数："+num);
            list.add(num);
        }
    }
}
