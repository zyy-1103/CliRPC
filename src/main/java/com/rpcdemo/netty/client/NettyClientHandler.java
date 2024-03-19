package com.rpcdemo.netty.client;

import com.rpcdemo.common.Protocol;
import com.rpcdemo.netty.dto.RpcMessage;
import com.rpcdemo.netty.dto.RpcRequest;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.crypto.interfaces.PBEKey;
import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext context;
    private Object result;
    private RpcRequest rpcRequest;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //被代理对象调用, 发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果 (3)-》5
    @Override
    public synchronized Object call() throws Exception {
//        context.writeAndFlush(para);
        RpcMessage rpcMessage = new RpcMessage(Protocol.REQUEST_TYPE, rpcRequest);
        ChannelFuture future = context.writeAndFlush(rpcMessage);
        future.addListener((ChannelFuture cf) -> {
            // 后续可以完善
            if (cf.isSuccess()) {
                System.out.println("Data sent successfully");
            } else {
                System.out.println("Failed to send data");
                cf.cause().printStackTrace();
            }
        });
        //进行wait 等待channelRead 方法获取到服务器的结果后，唤醒
        wait();
        //服务方返回的结果
        return  result;
    }

    void setPara(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }
}
