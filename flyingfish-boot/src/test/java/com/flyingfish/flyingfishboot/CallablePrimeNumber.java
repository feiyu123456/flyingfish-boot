package com.flyingfish.flyingfishboot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/5/29
 **/
public class CallablePrimeNumber {
    private static int threadPoolSize=4;
    private static int numberParts=4;
    private static AtomicInteger integer=new AtomicInteger();
    /**
     * 判断该数字是否是质数
     *
     * @param number
     * @return
     */
    public boolean isPrimeNumber(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <number ; i++) {
            if (number % i == 0) {
                return false;
            } else {
                continue;
            }
        }
        return true;
    }

    public int getPrimeNums(int start,int end) {
        for(int i=start;i<=end;i++) {
            if(isPrimeNumber(i)) {
                integer.getAndIncrement();
            }
        }
        return integer.get();
    }

    public void sumPrimeNums(int number) {
        int nums=number/numberParts;
        List<Callable<Integer>> callableList=new ArrayList<Callable<Integer>>();
        for(int i=0;i<numberParts;i++) {
            int start=i*nums+1;
            int end=(numberParts-i==1)?number:start+nums-1;
            callableList.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return getPrimeNums(start,end);
                }
            });
        }
        ExecutorService executor= Executors.newFixedThreadPool(threadPoolSize);
        try {
            executor.invokeAll(callableList,10000, TimeUnit.SECONDS );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println("count--------->"+integer.get());

    }

    public static void main(String[] args) {
        System.out.println(2 + 3 + 6 + 8 + 7 + 2 + 5+ 6 + 10 + 4 + 3 + 5 + 1);;

        System.out.println(2 + 4 + 6 + 8 + 1 + 2 + 7 + 4 + 3 + 5 + 1);

        System.out.println(2+3+8+9+1+2+7+4+3+5+1);
    }
}
