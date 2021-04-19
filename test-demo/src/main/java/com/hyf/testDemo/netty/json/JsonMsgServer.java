package com.hyf.testDemo.netty.json;

import cn.hutool.core.thread.ThreadUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/25
 */
public class JsonMsgServer {

    public static void main(String[] args){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4))
                                    .addLast(new StringDecoder(Charset.forName("UTF-8")))
                                    .addLast(new JsonMsgProcessorDecoder());
                        }
                    });
            // 调用 bind 方法监听指定端口号
            ChannelFuture future = bootstrap.bind(8080);
            future.addListener(future1 -> {
                if (future1.isSuccess()){
                    System.out.println("启动成功");
                }else {
                    System.out.println("启动失败");
                }
            });
            future.sync();
            ThreadUtil.sleep(Integer.MAX_VALUE);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
