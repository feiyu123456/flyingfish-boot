package com.flyingfish.flyingfishboot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/2/28
 **/
@Slf4j(topic = "t1.test")
public class JavaTest {
    int r = 0;
    int r1 = 0,r2=0;

    @Test
    public void mainTest() throws InterruptedException {


//        Runnable r = () -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                log.debug("wake up....");
//                e.printStackTrace();
//            }
//        };
//        Thread thread = new Thread(r, "t1");
//        thread.start();
//
//        Thread.sleep(1000);
//        log.debug("interrupt...");
//        thread.interrupt();

        test4();
    }

    public void test1() throws InterruptedException {
        log.debug("enter....");
        TimeUnit.SECONDS.sleep(1000);
    }

    public void test2() throws InterruptedException {
         log.debug("enter...");
        Runnable t = new Runnable() {
            @Override
            public void run() {
                try {
                    log.debug("开始");
                    Thread.sleep(2000);
                    log.debug("结束");
                    r = 10;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(t, "t1");
        t1.start();
        t1.join();
        log.debug("结果为：{}", r);
        log.debug("结束");
    }

    public void test3() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                r1 = 10;
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                r2 = 20;
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        t1.join();
        log.debug("t1 join..");
        t2.join();
        log.debug("t2 join..");
        long end = System.currentTimeMillis();
        log.debug("r1:{},r2:{},cost:{}", r1, r2, end-start);
    }

    public void test4() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                boolean flag = Thread.currentThread().isInterrupted();
                log.debug("我玩的挺嗨==={}",flag);
                //正常线程打断isInterrupt=true ,阻塞线程是false
                if (flag) {
                    log.debug("我不玩了 我被打断了！");
                    break;
                }
            }
        }, "t1");
        t1.start();
        Thread.sleep(3000);
        t1.interrupt();
        log.debug("t1 is interrupt ...");
    }

    private static Thread monitor;

    //两阶段终止
    @Test
    public void TwoPhaseTermination() throws InterruptedException {
         start();
         Thread.sleep(3500);
         stop();
    }

    public static void main(String[] args) throws InterruptedException {
         test5();
    }

    public static void start(){
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace(); 
                    //需要重新设置打断标识
                    current.interrupt();
                    //log.debug("===" + current.isInterrupted());
                }
            }
        }, "t1");
        monitor.start();
    }

    public static void stop() {
        monitor.interrupt();
    }

    //@Test
    public static void test5() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            //TimeUnit.SECONDS.sleep(1);
            log.debug("run....");
            LockSupport.park();
            log.debug("unPark....");
            log.debug("打断状态：{}", Thread.interrupted());
            // Thread.interrupted()重置标识为true
            LockSupport.park();
            log.debug("unPark....");
        }, "t1");

        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();//会让park失效
        log.debug("main....结束了！");
    }
}
