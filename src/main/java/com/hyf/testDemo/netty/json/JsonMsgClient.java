package com.hyf.testDemo.netty.json;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/25
 */
public class JsonMsgClient {

    public static void main(String[] args){
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("127.0.0.1",8080)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new LengthFieldPrepender(4))
                                    .addLast(new StringEncoder(Charset.forName("UTF-8")));
                        }
                    });
            // 调用 connect 方法进行连接
            ChannelFuture future = bootstrap.connect();
            future.addListener(future12 -> {
                if (future12.isSuccess()){
                    System.out.println("连接成功！");
                }else {
                    System.out.println("连接失败！");
                }
            });
            // 同步等待直到连接成功
            future.sync();
            Channel channel = future.channel();

            for (int i = 0; i < 10; i++) {
                JsonMsg jsonMsg = new JsonMsg(i,"我是"+i+"号 JsonMsg！");
                String json = JsonUtil.convertToJson(jsonMsg);
                System.out.println(json);
                channel.writeAndFlush(json);
            }
            channel.flush();
            ChannelFuture future1 = channel.closeFuture();
            future1.sync();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            workGroup.shutdownGracefully();
        }
    }
}
