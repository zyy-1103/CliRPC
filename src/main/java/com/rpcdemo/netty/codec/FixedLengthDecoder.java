package com.rpcdemo.netty.codec;

import com.rpcdemo.common.Protocol;
import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.dto.RpcResponse;
import com.rpcdemo.netty.serialize.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.checkerframework.checker.units.qual.K;

import javax.print.attribute.standard.RequestingUserName;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.List;

public class FixedLengthDecoder  extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        // 读取可读字节数
        int readableBytes = in.readableBytes();

        // 如果可读字节数小于信息头长度，则返回
        if (readableBytes < Protocol.HEAD_SIZE) {
            return;
        }

        byte type = in.readByte();
        int length = in.readInt();

        // 如果可读字节数小于消息长度，则返回
        if (readableBytes < length) {
            in.resetReaderIndex(); // 重置读指针
            return;
        }

        // 读取完整消息
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        if (type == Protocol.REQUEST_TYPE) {
            RpcRequest deserialize = KryoSerializer.deserialize(bytes, RpcRequest.class);
            out.add(deserialize);
        } else {
            RpcResponse deserialize = KryoSerializer.deserialize(bytes, RpcResponse.class);
            out.add(deserialize);
        }
    }
}
