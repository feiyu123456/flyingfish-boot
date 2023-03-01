package com.flyingfish.flyingfishboot;

import java.util.concurrent.TimeUnit;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/2/3
 **/
public class Test {
    private static boolean isStop = false;
    private static long i =0;

    private static void stopIt(){
        isStop = true;
    }

    public static void main(String[] args) {
        Object o = new Object();
        new thread1().start();
        while (!isStop) {
            System.out.println(111);
        }
        System.out.println(isStop);
    }

    static class thread1 extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopIt();
        }
    }
}
