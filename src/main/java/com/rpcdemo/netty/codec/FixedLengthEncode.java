package com.rpcdemo.netty.codec;

import com.rpcdemo.common.Protocol;
import com.rpcdemo.netty.dto.RpcMessage;
import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.serialize.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.concurrent.atomic.AtomicInteger;

public class FixedLengthEncode extends MessageToByteEncoder<RpcMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcMessage rpcMessage, ByteBuf byteBuf) throws Exception {
        byte[] serialize = KryoSerializer.serialize(rpcMessage.getData());
        byteBuf.writeByte(rpcMessage.getType());
        byteBuf.writeInt(serialize.length + Protocol.HEAD_SIZE);
        byteBuf.writeBytes(serialize);
    }
}
