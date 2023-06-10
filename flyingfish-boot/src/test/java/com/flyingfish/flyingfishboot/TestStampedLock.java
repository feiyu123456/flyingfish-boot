package com.flyingfish.flyingfishboot;

import com.flyingfish.entity.Student;
import com.flyingfish.interfacecustom.ExcelTitle;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/3/14
 **/
public class TestStampedLock {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Field[] fields = Student.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if("age".equalsIgnoreCase(fields[i].getName()))
                System.out.println(fields[i].getAnnotation(ExcelTitle.class).value());
        }

//        Method[] methods = FlyingFishInfo.class.getMethods();
//        for (int i = 0; i < methods.length; i++) {
//            System.out.println(methods[i].getName());
//        }
        Method[] methods = Student.class.getMethods();
        Class<Student> clazz = Student.class;
        Student s = clazz.newInstance();

        for (int i = 0; i < methods.length; i++) {
            if ("setName".equals(methods[i].getName())) methods[i].invoke(s, (String) "zhangSan");
        }
        System.out.println(s);
    }
}
