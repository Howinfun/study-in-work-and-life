package com.hyf.testDemo.netty.decoder.LengthFieldBasedFrameDecoder;

import cn.hutool.core.thread.ThreadUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/24
 */
public class TestLengthFieldBasedFrameDecoder1 {

    public static void main(String[] args) {

        // 长度+内容

        LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(1024,0,4,0,4);

        ChannelInitializer channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(decoder).addLast(new StringDecoder()).addLast(new StringProcessDecoder());
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);

        String content = "一起来学Netty吧！";
        int length = content.getBytes().length;
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(length);
            byteBuf.writeBytes(content.getBytes(Charset.forName("utf-8")));
            embeddedChannel.writeInbound(byteBuf);
        }

        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
