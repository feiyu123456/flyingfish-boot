package com.flyingfish.flyingfishboot;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/14
 **/
@Slf4j
public class TestSemaphore {

    public static void main(String[] args) {
        //semaphore commit 
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    log.debug("running....");
                    Thread.sleep(1000);
                    log.debug("end....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
