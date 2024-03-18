package com.rpcdemo.netty.server;

import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.dto.RpcResponse;
import com.rpcdemo.netty.serialize.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        Class<?> aClass = Class.forName(request.getInterfaceName());
        Method method = aClass.getMethod(request.getMethodName(), request.getParamTypes());
        Object invoke = method.invoke(aClass, request.getParameters());
        RpcResponse<Object> objectRpcResponse = new RpcResponse<>();
        objectRpcResponse.setData(invoke);
        objectRpcResponse.setData(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
