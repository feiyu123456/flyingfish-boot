package com.flyingfish.custom;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/6/13
 **/
public class StaticValid {
    private static String STR_BEFORE_CODE = "在静态代码块前面的变量";

    static
    {
        System.out.println(STR_BEFORE_CODE);
        STR_AFTER_CODE = "asads";
    }

    private static String STR_AFTER_CODE;

    public static void main(String[] args)
    {
        System.out.println("---------");
        System.out.println(STR_AFTER_CODE);
        System.out.println("---------");
    }
}
