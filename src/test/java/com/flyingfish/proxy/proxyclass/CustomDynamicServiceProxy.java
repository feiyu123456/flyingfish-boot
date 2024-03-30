package com.flyingfish.proxy.proxyclass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomDynamicServiceProxy implements InvocationHandler {

    private Object object;


    public CustomDynamicServiceProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoke");
        Object result = method.invoke(object, args);
        System.out.println("Before invoke");
        return result;
    }
}
