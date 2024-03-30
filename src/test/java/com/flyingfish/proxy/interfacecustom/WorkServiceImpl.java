package com.flyingfish.proxy.interfacecustom;

public class WorkServiceImpl implements WorkService{

    @Override
    public void code() {
        System.out.println("编码");
    }

    @Override
    public void check() {
        System.out.println("打卡");
    }
}
