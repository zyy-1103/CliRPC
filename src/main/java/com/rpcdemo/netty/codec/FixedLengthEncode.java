package com.rpcdemo.netty.codec;

import com.rpcdemo.common.Protocol;
import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.serialize.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class FixedLengthEncode extends MessageToByteEncoder<RpcRequest> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest, ByteBuf byteBuf) throws Exception {
        byte[] serialize = KryoSerializer.serialize(rpcRequest);
        // 消息类型为Request
        byteBuf.writeByte(KryoSerializer.REQUEST);
        // 消息长度
        byteBuf.writeInt(serialize.length + Protocol.HEAD_SIZE);
        // 消息正文
        byteBuf.writeBytes(serialize);
    }
}
