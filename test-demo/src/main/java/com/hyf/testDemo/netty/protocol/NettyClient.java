package com.hyf.testDemo.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/26
 */
public class NettyClient {

    public static void main(String[] args) {
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
                            channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder());
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
                JsonMsgProto.Msg msg = build(i,"你好世界"+i);
                channel.writeAndFlush(msg);
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

    public static JsonMsgProto.Msg build(int id,String content){
        JsonMsgProto.Msg.Builder builder = JsonMsgProto.Msg.newBuilder();
        builder.setId(id);
        builder.setContent(content);
        JsonMsgProto.Msg msg = builder.build();
        return msg;
    }
}
