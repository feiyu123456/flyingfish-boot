package com.flyingfish.flyingfishboot;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/7
 **/
@Slf4j(topic = "t2.test")
public class JavaTestTwo {

    private static ReentrantLock reentrantLock = new ReentrantLock();

    static final Object lock = new Object();
    static boolean t2Run = false;
    
    public void test1() throws InterruptedException{
        Thread t1 = new Thread(() -> {
            log.debug("尝试获取锁。。。");
            try {
                if (!reentrantLock.tryLock(1, TimeUnit.SECONDS)) {
                    log.debug("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                log.debug("获取不到锁");
                e.printStackTrace();
                return;
            }

            try {
                log.debug("获得到锁");
            } finally {
                reentrantLock.unlock();
            }
        }, "t1");

        reentrantLock.lock();
        log.debug("获得到锁");
        t1.start();
        reentrantLock.unlock();
        TimeUnit.SECONDS.sleep(1);
    }

    //固定运行顺序-wait-ify
    public static void test2() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2Run) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2Run = true;
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

    //固定运行顺序-park-unPark
    public static void test3(){

    }

    //等待标记
    private int flag;
    //循环次数
    private int loopNumber;
    //交替输出-wait-ify
    public static void test4() {
        

    }

    public static void test5() {
        AtomicInteger i = new AtomicInteger(0);
        i.updateAndGet(x -> x*10);
        System.out.println(i.get());
    }

    static AtomicReference<String> reference = new AtomicReference<>("A");
    public static void test6() throws InterruptedException {
        log.debug("我开始打印了。。。");
        String str = reference.get();
        other();
        TimeUnit.SECONDS.sleep(2);
        log.debug("A to C == {}", reference.compareAndSet("A", "C"));
    }

    public static void other(){
        new Thread(() -> {
            log.debug("B to A  === {}", reference.compareAndSet("B", "A"));
        }, "t2").start();
        new Thread(() -> {
            log.debug("A to B  === {}", reference.compareAndSet("A", "B"));
        }, "t1").start();

    }

    public final static Object a = new Object();
    public final static Object b = new Object();
    //死锁
    public static void test7(){
        Thread t1 = new Thread(() -> {
            synchronized (a) {
                log.debug("lock a");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    log.debug("lock b");
                    log.debug("操作。。。");
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            synchronized (b) {
                log.debug("lock b");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    log.debug("lock a");
                    log.debug("操作。。。");
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }

    static Condition waitCigaretteQueue = reentrantLock.newCondition();
    static Condition waitbreakfastQueue = reentrantLock.newCondition();
    static volatile boolean hasCigrette = false;
    static volatile boolean hasBreakfast = false;
    public static void test8() throws InterruptedException {
        new Thread(() -> {
            try {
                reentrantLock.lock();
                while (!hasCigrette) {
                    try {
                        waitCigaretteQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("等到了它的烟");
            } finally {
                reentrantLock.unlock();
            }
        }).start();
        new Thread(() -> {
            try {
                reentrantLock.lock();
                while (!hasBreakfast) {
                    try {
                        waitbreakfastQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("等到了它的早餐");
            } finally {
                reentrantLock.unlock();
            }
        }).start();
        Thread.sleep(1000);
        sendBreakfast();
        Thread.sleep(1000);
        sendCigarette();
    }

    private static void sendCigarette() {
        reentrantLock.lock();
        try {
            log.debug("送烟来了");
            hasCigrette = true;
            waitCigaretteQueue.signal();
        } finally {
            reentrantLock.unlock();
        }
    }
    private static void sendBreakfast() {
        reentrantLock.lock();
        try {
            log.debug("送早餐来了");
            hasBreakfast = true;
            waitbreakfastQueue.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void heapTests() throws InterruptedException {
        List<JavaTestTwo> heapTests = new ArrayList<>();
        while (true) {
            heapTests.add(new JavaTestTwo());
            Thread.sleep(10);
        }
    }

    public static void testThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "myPool_t" + t.getAndIncrement());
            }
        });

        pool.execute(() -> {
             log.debug("1");
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });

        Future<String> future = pool.submit(() -> {
            log.debug("running...");
            Thread.sleep(1000);
            return "ok";
        });

        Future<?> futureTwo = pool.submit(() -> {log.debug("runnable run ....");return "hello";});

        log.debug(future.get());
        log.debug((String) futureTwo.get());
    }

    static boolean flagTwo = false;
    public static void main(String[] args) throws InterruptedException {
//        testCachedThreadPool();
//        Condition condition = reentrantLock.newCondition();
//        new Thread(() ->{
//            reentrantLock.lock();
//            try {
//                log.debug("t1 start....");
//                while (!flagTwo){
//                    log.debug("t1 await....");
//                    condition.await();
//                    log.debug("t1 end....");
//                }
//                if (flagTwo) log.debug("t1 doing.....");
//                log.debug("t1 doing....");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                reentrantLock.unlock();
//            }
//        }, "t1").start();
//
//        new Thread(() ->{
//             reentrantLock.lock();
//             try {
//                 log.debug("t2 start....");
//                  while (!flagTwo) {
//                      Thread.sleep(2000);
//                      flagTwo = true;
//                  }
//                  if (flagTwo) condition.signal();
//                  log.debug("t2 end....");
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             } finally {
//                 reentrantLock.unlock();
//             }
//        }, "t2").start();
//
//        StampedLock stampedLock = new StampedLock();

        test2();
    }

    public static void testFixedThreadPool() {
        //fixed thread number
        Executor fixedPool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "pool-" + t.incrementAndGet());
            }
        });
        fixedPool.execute(() -> log.debug("task one!"));
        fixedPool.execute(() -> log.debug("task two!"));
        fixedPool.execute(() -> log.debug("task three!"));
    }

    //not have coreThread
    public static void testCachedThreadPool() {
        final Object object = new Object();
        ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool(new ThreadFactory() {
             private AtomicInteger t = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "thread-" + t.incrementAndGet());
            }
        });
        //cachedThreadPool.setCorePoolSize();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            cachedThreadPool.execute(() -> {
                //synchronized (object) {
                    log.debug("task {}", finalI +1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                //}
            });
        }
    }



}

class MyTask extends RecursiveTask<String> {

    @Override
    protected String compute() {
        return null;
    }
}
