package com.rpcdemo.netty.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.dto.RpcResponse;

import java.io.ByteArrayInputStream;

public class KryoSerializer {

    private static final Kryo kryo = new Kryo();
    public static final int REQUEST=1;
    public static final int RESPONSE=2;

    static {
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
    }

    public static byte[] serialize(Object o) {
        // -1不限制大小
        Output output = new Output(4096, -1);
        kryo.writeObject(output, o);
        byte[] bytes = output.toBytes();
        output.close();
        return bytes;
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        Object o = kryo.readObject(input, clazz);
        return clazz.cast(o);
    }

}
