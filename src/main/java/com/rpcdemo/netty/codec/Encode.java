package com.rpcdemo.netty.codec;

import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.serialize.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encode extends MessageToByteEncoder<RpcRequest> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest, ByteBuf byteBuf) throws Exception {
        byte[] serialize = KryoSerializer.serialize(rpcRequest);
        // 消息类型为Request
        byteBuf.writeInt(KryoSerializer.REQUEST);
        byteBuf.writeBytes(serialize);
    }
}
