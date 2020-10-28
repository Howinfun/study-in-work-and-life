package com.hyf.testDemo.netty.encoder;

import cn.hutool.core.thread.ThreadUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/24
 */
public class String2IntegerEncoder extends MessageToMessageEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {

        System.out.println("对 "+ s + " 进行解码！");
        char[] charArray = s.toCharArray();
        for (char c : charArray) {
            if (c >= 48 & c <= 57){
                String sc = String.valueOf(c);
                list.add(Integer.valueOf(sc));
            }
        }
    }

    public static void main(String[] args) {
        ChannelInitializer channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new Integer2ByteEncoder()).addLast(new String2IntegerEncoder());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(channelInitializer);
        for (int i = 0; i < 10; i++) {
            channel.writeOutbound("I am the "+i);
        }
        channel.flushOutbound();
        ByteBuf byteBuf = channel.readOutbound();
        while (byteBuf != null){

            System.out.println("拿到了整数的编码："+byteBuf.readInt());
            byteBuf = channel.readOutbound();
        }

        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
