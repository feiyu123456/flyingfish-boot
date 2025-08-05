package com.flyingfish.proxy.interfacecustom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl implements HelloService{

    @Override
    public void sayHello(String name) {
        log.info("Hello " + name);
    }

    @Override
    public void sayNo(String name) {
        log.info("no " + name);
    }
}
