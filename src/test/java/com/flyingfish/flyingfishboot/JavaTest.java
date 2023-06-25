package com.flyingfish.flyingfishboot;

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
        messageTest();
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

    static int counter = 0;
    static Object object = new Object();

    public static void test6() throws InterruptedException {
        //cpu分时系统时间片上下文切换 导致的共享变量不安全
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (object) {
                    counter++;
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (object) {
                    counter--;
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("counter:=======" + counter);
    }

    @Test
    public void test7() throws InterruptedException {
        Account a1 = new Account(1000);
        Account a2 = new Account(1000);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 1000) {
                    a1.toTransfer(a2, new Random().nextInt(100));
                    i++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 1000) {
                    a2.toTransfer(a1, new Random().nextInt(100));
                    i++;
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("total: {}", a1.getMoney() + a2.getMoney());
    }

    @Test
    public void testBuffer() {
        StringBuffer b = new StringBuffer();
        System.out.println(b.substring(0, b.length() - 1));
    }

    //保护性暂停
    public static void guardedObject() {
        GuardedObject guardedObject = new GuardedObject();
        //线程1 等待线程2的结果
        new Thread(() -> {
            //等待线程结果
            log.debug("等待结果");
            Object response = guardedObject.get(2000);
            log.debug("response={}", response);
        },"t1").start();

        new Thread(() -> {
            //等待线程结果
            log.debug("执行下载");
            try {
                //guardedObject.complete(new ArrayList<String>(){{add("hello");add("world");}});
                guardedObject.complete(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }

    //生产者消费者
    public static void messageTest() {
        MessageQueue queue = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.put(new Message(id + 1, "值"+id+1));
            }, "生产者").start();
        }
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
    }

}

@Slf4j
class MessageQueue {

    private LinkedList<Message> list = new LinkedList<>();
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    public Message take() {
        synchronized (list) {
            while (list.isEmpty()) {
               try {
                   log.debug("队列为空，消费组线程等待");
                   list.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            }
            Message message = list.removeFirst();
            log.debug("消费者消费消息：{}",message);
            list.notifyAll();
            return message;
        }
    }

    public void put(Message message){
         synchronized (list) {
             //检查队列是否已满
             while (list.size() == capcity) {
                 try {
                     log.debug("队列已满，生产者线程等待");
                     list.wait();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
             list.addLast(message);
             log.debug("生产者消息：{}", message);
             list.notifyAll();
         }
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Message {
    private int id;
    private Object value;
}

@Slf4j
class GuardedObject {
    //结果
    private Object response;

    /**
     *
     * @param timeout 等待超时时间
     * @return
     */
    //给虚假唤醒增加超时暂停
    public Object get(long timeout){
        synchronized (this) {
            long startTime = System.currentTimeMillis();
            long delay = 0l;
            while (response == null) {

                if (timeout <= delay) {
                    log.debug("我不等了！我等待timeout了！delay={}", delay);
                    break;
                }
                try {
                    this.wait(timeout - delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                delay = System.currentTimeMillis() - startTime;
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response) throws InterruptedException {
        synchronized (this) {
            this.response = response;
            TimeUnit.SECONDS.sleep(5);
            this.notify();
        }
    }
}

class Account{
    int money;

    AtomicInteger i = new AtomicInteger();

    public Account(int i) {
        this.money = i;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int toTransfer(Account targetAccount, int num) {
        synchronized (Account.class) {
            if (this.money > num) {
                this.setMoney(this.money - num);
                targetAccount.setMoney(targetAccount.getMoney() + num);
                return num;
            } else {
                return 0;
            }
        }
    }
}
