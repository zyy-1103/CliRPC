package com.rpcdemo.netty.server;

import com.rpcdemo.common.Protocol;
import com.rpcdemo.netty.dto.RpcMessage;
import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.dto.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 执行对应的方法
        RpcRequest request = (RpcRequest) msg;
        Class<?> aClass = Class.forName(request.getInterfaceName());
        Method method = aClass.getMethod(request.getMethodName(), request.getParamTypes());
        Object invoke = method.invoke(aClass, request.getParameters());
        RpcResponse<Object> response = new RpcResponse<>();
        response.setData(invoke);
        response.setID(request.getID());

        // 响应请求
//        ByteBuf buffer = ctx.alloc().buffer();
//        buffer.writeBytes(KryoSerializer.serialize(response));
//        ChannelFuture future = ctx.writeAndFlush(buffer);
        RpcMessage rpcMessage = new RpcMessage(Protocol.RESPONSE_TYPE, response);
        ChannelFuture future = ctx.writeAndFlush(rpcMessage);
        future.addListener((ChannelFutureListener) listen->{
            if (listen.isSuccess()) {
                // 预留
                System.out.println("返回成功！");
            }
            ctx.close();
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
