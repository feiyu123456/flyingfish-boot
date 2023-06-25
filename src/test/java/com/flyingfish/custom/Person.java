package com.flyingfish.custom;

import java.sql.Timestamp;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/6/13
 **/
public class Person {

    public static String arg01;

    private String arg02 = "----父类非静态成员变量arg02----";

    //静态代码块
    static {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            System.out.println("------父类的静态代码块执行------" + timestamp);
            arg01 = "---父类的静态变量arg01---";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //非静态代码块
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            System.out.println("------父类的非静态代码块执行------" + timestamp);
            System.out.println(arg02);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //构造方法
    Person() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            Thread.sleep(1000);
            System.out.println("------父类的构造方法执行------" + timestamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
