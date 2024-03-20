package com.rpcdemo.netty.server;

import com.rpcdemo.netty.codec.FixedLengthDecoder;
import com.rpcdemo.netty.codec.FixedLengthEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;


public class NettyServer {
    @Value("${netty.address}")
    private String address;

    @Value("${netty.port}")
    private int port;
    EventLoopGroup boss;
    EventLoopGroup worker;

    public void run() throws Exception {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss, worker)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new FixedLengthDecoder());
                                pipeline.addLast(new FixedLengthEncode());
                                pipeline.addLast(new NettyServerHandler());
                            }
                        }
                );
        ChannelFuture future = b.bind(port).sync();
        future.channel().closeFuture().sync();
    }

    @PreDestroy
    public void destory() throws InterruptedException {
        boss.shutdownGracefully().sync();
        worker.shutdownGracefully().sync();
    }

}
