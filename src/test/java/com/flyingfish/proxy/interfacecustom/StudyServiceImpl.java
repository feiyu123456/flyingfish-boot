package com.flyingfish.proxy.interfacecustom;

public class StudyServiceImpl implements StudyService{
    
    @Override
    public void read() {
        System.out.println("READ!");
    }

    @Override
    public void write() {
        System.out.println("WRITE!");
    }
}
