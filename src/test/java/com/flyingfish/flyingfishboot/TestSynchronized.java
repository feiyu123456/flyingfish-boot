package com.flyingfish.flyingfishboot;

import lombok.SneakyThrows;
import org.junit.Test;

import java.lang.reflect.Field;

public class TestSynchronized {

    @Test
    public void test1() throws InterruptedException {
        STest sTest = new STest();

        Thread thread01 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                //sTest.wait();
                sTest.print();
            }
        }, "thread01");

        Thread thread02 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                //可以通过反射获取类对象的中私有成员变量
                //synchronized (getPrivateFiled(sTest, "object")) {
                synchronized (sTest) {
                    //sTest.notify();
                    while (true) ;
                }
            }
        }, "thread02");

        thread02.start();
        Thread.sleep(3000);
        thread01.start();

    }

    public Object getPrivateFiled(Object instance, String filedName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(instance);
    }
}

class STest{
    private final Object object = new Object();

    public void print() {
        //synchronized (this)锁对象是该类的对象，其他线程很容易获取该对象 通过synchronized (sTest)造成死锁问题。
        //synchronized (this) {
        synchronized (object) {
            System.out.println("sb hello!");
        }
    }
}
