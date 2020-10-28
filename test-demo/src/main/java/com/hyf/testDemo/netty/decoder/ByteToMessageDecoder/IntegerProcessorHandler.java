package com.hyf.testDemo.netty.decoder.ByteToMessageDecoder;

import cn.hutool.core.thread.ThreadUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/21
 */
public class IntegerProcessorHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Integer integer = (Integer) msg;
        System.out.println("打印出一个整数："+integer);
    }

    public static void main(String[] args) {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new MyIntegerDecoder()).addLast(new IntegerProcessorHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        for (int j = 0;j < 20;j++){
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(j);
            channel.writeInbound(byteBuf);
        }
        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
