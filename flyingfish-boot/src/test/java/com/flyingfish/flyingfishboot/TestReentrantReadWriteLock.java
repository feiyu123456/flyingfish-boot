package com.flyingfish.flyingfishboot;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/14
 **/
@Slf4j
public class TestReentrantReadWriteLock {
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final static ReentrantReadWriteLock.ReadLock r = lock.readLock();
    final static ReentrantReadWriteLock.WriteLock w = lock.writeLock();

    public static void read() {
        log.debug("read lock....");
        r.lock();
        try {
            //w.lock();
            log.debug("reading....");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("read unlock...");
            r.unlock();
        }
    }

    public static void write(){
        log.debug("write lock....");
        w.lock();
        try {
            log.debug("writing....");
        } finally {
            log.debug("write unlock...");
            w.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            read();
        }, "t1").start();
        Thread.sleep(100);
        new Thread(() -> {
            write();
        }, "t2").start();
    }
}
