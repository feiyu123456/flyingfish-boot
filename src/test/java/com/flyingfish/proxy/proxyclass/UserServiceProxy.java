package com.flyingfish.proxy.proxyclass;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UserServiceProxy implements MethodInterceptor {

    private Object object;

    public Object createProxy(Object object) {
        this.object = object;
        Enhancer enhancer = new Enhancer();
        //设置被代理的类
        enhancer.setSuperclass(this.object.getClass());
        //设置拦截器
        enhancer.setCallback(this);
        //返回代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始事务");
        //调用目标方法
        Object result = method.invoke(o, objects);
        System.out.println("提交事务");
        return result;
    }
}
