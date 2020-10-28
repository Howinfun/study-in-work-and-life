package com.hyf.testDemo.netty.decoder.ReplayingDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/21
 */
public class MyIntegerDecoder2 extends ReplayingDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        /**
         * 使用 ReplayingDecoder 和 ByteToMessageDecoder 最大的不同时是
         * ReplayingDecoder 对ByteBuf缓冲区进行了装饰，这个类名为ReplayingDecoderBuffer。该装饰器的特点是：在缓冲区真正读数据之前，首先进行长度的判断：如果长度合格，则读取数据；否则，抛出ReplayError。
         * ReplayingDecoder捕获到ReplayError后，会留着数据，等待下一次IO事件到来时再读取。
         */
        int num = byteBuf.readInt();
        System.out.println("解码出一个整数："+num);
        list.add(num);
    }
}
