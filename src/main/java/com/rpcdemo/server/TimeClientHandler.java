package com.rpcdemo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        ctx.close();
        //        ByteBuf buf = (ByteBuf) msg;
//        try {
//            long l = buf.readLong();
//            System.out.println(new SimpleDateFormat("yyyy:MM:dd EEEE").format(new Date(l)));
//            ctx.close();
//        }finally {
//            buf.release();
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
