package com.flyingfish.proxy.proxyclass;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class CustomDynamicServiceProxy implements InvocationHandler {

    private Object object;


    public CustomDynamicServiceProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Before invoke");
        Object result = method.invoke(object, args);
        log.info("After invoke");
        return result;
    }
}
