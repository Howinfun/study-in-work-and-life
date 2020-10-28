package com.hyf.testDemo.netty.json;

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
 * @date 2020/8/25
 */
public class JsonMsgTest {

    public static void main(String[] args) {
        ChannelInitializer channelInitializer = new ChannelInitializer<EmbeddedChannel>(){

            @Override
            protected void initChannel(EmbeddedChannel embeddedChannel) throws Exception {
                embeddedChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4))
                        .addLast(new StringDecoder(Charset.forName("UTF-8")))
                        .addLast(new JsonMsgProcessorDecoder());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(channelInitializer);
        for (int i = 0; i < 10; i++) {
            JsonMsg jsonMsg = new JsonMsg(i,"我是"+i+"号 JsonMsg！");
            String json = jsonMsg.convertToJson();
            System.out.println(json);
            byte[] bytes = json.getBytes();
            ByteBuf byteBuf = Unpooled.buffer();
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
            channel.writeOneInbound(byteBuf);
        }
        channel.flushInbound();
        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
