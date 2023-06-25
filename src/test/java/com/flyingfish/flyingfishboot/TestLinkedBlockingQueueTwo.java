package com.flyingfish.flyingfishboot;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author feiye
 * @date 2023/3/15 -
 **/
@Slf4j
public class TestLinkedBlockingQueueTwo {

    final static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

    public static void main(String[] args) {
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        AtomicLong l = new AtomicLong();
        //生产线程
        fixedPool.execute(() -> {
            while (true) {
                try {
                    String msg = "====" + Math.random() * 100;
                    queue.put(msg);
                    log.debug("【{}】我生产了===msg:{} 队列数量size：{}", Thread.currentThread().getName(), msg, queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //消费线程
        fixedPool.execute(() -> {
            while (true) {
                try {
                    String msg = queue.take();
                    log.debug("【{}】我消费了===msg:{} 队列数量size：{}", Thread.currentThread().getName(), msg, queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        fixedPool.execute(() -> {
            while (true) {
                try {
                    String msg = queue.take();
                    log.debug("【{}】我消费了===msg:{} 队列数量size：{}", Thread.currentThread().getName(), msg, queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
