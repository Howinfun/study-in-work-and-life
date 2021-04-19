package com.hyf.testDemo.netty.codec;

import cn.hutool.core.thread.ThreadUtil;
import com.hyf.testDemo.netty.decoder.ByteToMessageDecoder.IntegerProcessorHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/24
 */
public class IntegerCodec extends ByteToMessageCodec<Integer> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Integer integer, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(integer);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() >= 4){
            list.add(byteBuf.readInt());
        }
    }

    public static void main(String[] args) {
        ChannelInitializer channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new IntegerCodec()).addLast(new IntegerProcessorHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(channelInitializer);
        for (int i = 0; i < 10; i++) {
            channel.writeOneInbound(i);
        }
        channel.flushInbound();

        for (int i = 0; i < 10; i++) {
            channel.writeOneOutbound(i);
        }
        channel.flushOutbound();
        ByteBuf byteBuf = channel.readOutbound();
        while (byteBuf != null){
            Integer integer = byteBuf.readInt();
            System.out.println("得到一个整数："+integer);
            byteBuf = channel.readOutbound();
        }
        ThreadUtil.sleep(Integer.MAX_VALUE);


    }
}
