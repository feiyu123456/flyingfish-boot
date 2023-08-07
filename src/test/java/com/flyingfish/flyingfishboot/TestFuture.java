package com.flyingfish.flyingfishboot;

import org.junit.Test;

import java.util.concurrent.*;

public class TestFuture {

    @Test
    public void testFuture() throws ExecutionException, InterruptedException, TimeoutException {
        Callable call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    System.out.println("我先随眠3秒！");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "我被调用了";
            }
        };

        FutureTask<String> future = new FutureTask<String>(call);
        new Thread(future).start();
        try {
            System.out.println(future.get(1, TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            System.out.println(e.getMessage());
        }
    }
}
