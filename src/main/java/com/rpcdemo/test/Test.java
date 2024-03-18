package com.rpcdemo.test;


import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.netty.dto.RpcResponse;
import com.rpcdemo.netty.serialize.KryoSerializer;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {

    public static <T> T getT(Class<?> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = tClass.getConstructor();

        return (T) constructor.newInstance();
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RpcResponse<String> rpcResponse = new RpcResponse<>();
        rpcResponse.setData("abc");
        byte[] serialize = KryoSerializer.serialize(rpcResponse);
        RpcResponse deserialize = KryoSerializer.deserialize(serialize, RpcResponse.class);
        System.out.println(deserialize.getData());

    }
}
