package com.hyf.testDemo.netty.encoder;

import cn.hutool.core.thread.ThreadUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/24
 */
public class Integer2ByteEncoder extends MessageToByteEncoder<Integer> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Integer integer, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(integer);
        //System.out.println("编码了一个整数："+integer);
    }

    public static void main(String[] args) {
        ChannelInitializer channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new Integer2ByteEncoder());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(channelInitializer);
        for (int i = 0; i < 10; i++) {
            channel.writeOutbound(i);
        }
        channel.flushOutbound();
        ByteBuf byteBuf = channel.readOutbound();
        while (byteBuf != null){
            System.out.println("拿到了整数："+byteBuf.readInt());
            byteBuf = channel.readOutbound();
        }

        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
