package com.flyingfish.custom;

import java.sql.Timestamp;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/6/13
 **/
public class Student extends Person{
    static {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            System.out.println("------Student类的静态代码块执行------" + timestamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            System.out.println("------Student类的非静态代码块执行------" + timestamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Student() {
        super();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            System.out.println("------Student类的构造方法执行------" + timestamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
