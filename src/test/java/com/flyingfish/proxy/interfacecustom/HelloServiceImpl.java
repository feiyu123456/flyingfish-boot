package com.flyingfish.proxy.interfacecustom;

public class HelloServiceImpl implements HelloService{

    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }

    @Override
    public void sayNo(String name) {
        System.out.println("no " + name);
    }
}
