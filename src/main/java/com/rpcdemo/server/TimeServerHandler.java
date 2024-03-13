package com.rpcdemo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer(8);
        buffer.writeLong(System.currentTimeMillis());
        ChannelFuture future = ctx.writeAndFlush(buffer);
        future.addListener((ChannelFutureListener) listen->{
            if (listen.isSuccess()) {
                System.out.println("返回成功！");
            }
            ctx.close();
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
