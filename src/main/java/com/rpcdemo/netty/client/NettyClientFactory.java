package com.rpcdemo.netty.client;

import com.rpcdemo.netty.codec.FixedLengthDecoder;
import com.rpcdemo.netty.codec.FixedLengthEncode;
import com.rpcdemo.netty.dto.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClientFactory<T> {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Class<T> clazz;
    private static String host = "127.0.0.1" ;
    private static int port = 8080;

    public NettyClientFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getBean(RpcRequest request) {

        //创建 netty客户端 与服务端交互 并让动态代理类持有
        final NettyClientHandler clientHandler = this.getClientHandler();
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{clazz}, (proxy, method, args) -> {

                    //设置要发给服务器端的信息 接口的全类名#方法名#参数
                    clientHandler.setPara(request);
                    return executor.submit(clientHandler).get();
                });

    }

    private NettyClientHandler getClientHandler() {
        NettyClientHandler nettyClientHandler = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new FixedLengthDecoder());
                                pipeline.addLast(new FixedLengthEncode());
                                pipeline.addLast(nettyClientHandler);
                            }
                        }
                );

        try {
            bootstrap.connect(host,port).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nettyClientHandler;
    }
}
