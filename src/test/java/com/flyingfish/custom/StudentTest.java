package com.flyingfish.custom;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/6/13
 **/
public class StudentTest {
    public static void main(String[] args) {
        //new Student();
        //System.out.println(Person.arg01);
//        String a = new String("lisi");
//        String b = a.intern();
//        System.out.println(a == b);

        String a = "li";
        String b = a + "zhi";
        String d = new String("lizhi");
        // 结果为true
        System.out.println(b == d);
    }
}
