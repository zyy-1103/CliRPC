package com.rpcdemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");



        System.out.println("after");
    }

    public <T> T getProxy(String interfaceName) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(interfaceName);
        return (T) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), this);
    }
}
