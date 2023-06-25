package com.flyingfish.flyingfishboot;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/2/9
 **/
public class TestVolatile {
    public volatile int inc = 0;

    public void incease() {
        inc++;//非原子操作
    }

    public static void main(String[] args) {
        TestVolatile test = new TestVolatile();
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        test.incease();
                    }
                }
            }).start();
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(test.inc);
    }
}
