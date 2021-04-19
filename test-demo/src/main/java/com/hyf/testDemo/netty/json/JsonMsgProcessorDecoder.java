package com.hyf.testDemo.netty.json;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/25
 */
public class JsonMsgProcessorDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String json = (String) msg;
        JsonMsg jsonMsg = JsonUtil.parseFromJson(json,JsonMsg.class);
        System.out.println("得到一个 JsonMsg："+jsonMsg.toString());
    }
}
