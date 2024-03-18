package com.rpcdemo.proxy;

import com.rpcdemo.netty.client.NettyClient;
import com.rpcdemo.netty.dto.RpcRequest;
import com.rpcdemo.utils.IDGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {
    final String addr = "127.0.0.1";
    final int port = 8080;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        NettyClient nettyClient = new NettyClient(addr, port);
        RpcRequest rpcRequest = new RpcRequest(IDGenerator.getID(), method.getDeclaringClass().getName()
                , method.getName(), args, method.getParameterTypes());
        nettyClient.sendMessage(rpcRequest);
        System.out.println("after");
        return null;
    }

    public <T> T getProxy(String interfaceName) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(interfaceName);
        return (T) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), this);
    }
}
