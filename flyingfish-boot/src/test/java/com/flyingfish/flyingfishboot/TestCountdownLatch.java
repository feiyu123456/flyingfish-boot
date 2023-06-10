package com.flyingfish.flyingfishboot;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/14
 **/
@Slf4j
public class TestCountdownLatch {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            log.debug("t1 start.....");
            countDownLatch.countDown();
            log.debug("t1 end....., latch count:{}", countDownLatch.getCount());
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2 start.....");
            countDownLatch.countDown();
            log.debug("t2 end....., latch count:{}", countDownLatch.getCount());
        }, "t2").start();

        new Thread(() -> {
            log.debug("t3 start.....");
            countDownLatch.countDown();
            log.debug("t3 end....., latch count:{}", countDownLatch.getCount());
        }, "t3").start();

        log.debug("main start....");
        countDownLatch.await();
        log.debug("main end.....");

    }
}
