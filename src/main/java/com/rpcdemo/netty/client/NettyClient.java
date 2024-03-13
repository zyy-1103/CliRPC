package com.rpcdemo.netty.client;

import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.serialize.KryoSerializer;
import com.rpcdemo.server.TimeClientDecode;
import com.rpcdemo.server.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class NettyClient {
    EventLoopGroup worker;
    Bootstrap bootstrap;
    Channel channel;
    public NettyClient(String addr,int port) {
        worker = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(worker)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new TimeClientDecode(), new TimeClientHandler());
                    }
                });
        ChannelFuture connect = bootstrap.connect(addr, port);
        channel=connect.channel();
    }

    public void sendMessage(RpcRequest rpcRequest) throws InterruptedException {

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(KryoSerializer.REQUEST);
        buf.writeBytes(KryoSerializer.serialize(rpcRequest));
        channel.closeFuture().sync();


    }

    public void close() {
        worker.shutdownGracefully();
    }
}
