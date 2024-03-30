package com.flyingfish.proxy.test;

import com.flyingfish.proxy.interfacecustom.*;
import com.flyingfish.proxy.proxyclass.CustomDynamicServiceProxy;
import com.flyingfish.proxy.proxyclass.CustomStaticServiceProxy;
import com.flyingfish.proxy.proxyclass.UserServiceProxy;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class TestProxy {

    //静态代理test
    @Test
    public void testProxy01() {
        CustomStaticServiceProxy proxy = new CustomStaticServiceProxy(new StudyServiceImpl());
        CustomStaticServiceProxy proxy2 = new CustomStaticServiceProxy(new WorkServiceImpl());
        proxy.write();
        proxy.read();
        proxy2.code();
        proxy2.check();
    }

    //动态代理test reflect
    @Test
    public void testProxy02(){
        HelloService service = new HelloServiceImpl();
        //创建调用处理器
        CustomDynamicServiceProxy handler = new CustomDynamicServiceProxy(service);
        HelloService proxy = (HelloService) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), handler);
        proxy.sayHello("flyingFish");
        proxy.sayNo("SB");
    }

    //cglib动态代理
    @Test
    public void testProxy03(){
        UserService service = new UserService();
        UserServiceProxy userServiceProxy = new UserServiceProxy();
        UserService proxy= (UserService) userServiceProxy.createProxy(service);
        proxy.save();
    }

}
