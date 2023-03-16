package com.flyingfish.flyingfishboot;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/13
 **/
@Slf4j
public class TestAqs {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(() -> {
            lock.lock();
            log.debug("owner : {}", lock.isHeldExclusively());
            log.debug("locking....");
            System.out.println("locking....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.debug("unlocking....");
                lock.unlock();
            }
        },"t1").start();
        
        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking....");
            } finally {
                log.debug("unlocking....");
                lock.unlock();
            }
        },"t2").start();
        
        log.debug("owner : {}", lock.isHeldExclusively());

    }
    
}

class MyLock implements Lock {

    class MySync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        public Condition newCondition() {
           return new ConditionObject();
        }
    }

    private MySync sync = new MySync();
    
    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
       sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public boolean isHeldExclusively() {
        return sync.isHeldExclusively();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
