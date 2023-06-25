package com.flyingfish.annotation;


import java.lang.annotation.*;
import java.lang.reflect.Method;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotationOne {
    String[] value() default "unknown";
}

class  Person {

    @MyAnnotationOne
    @Deprecated
    public void empty(){
        System.out.println("\nempty");
    }

    @MyAnnotationOne(value = {"sb", "cb"})
    public void someBody(String arg1, String arg2) {
        System.out.println("\narg1：" + arg1 + "--------arg2：" + arg2);
    }

}

class AnnotationTest{
//    public static void main(String[] args) throws Exception{
//        Person person = new Person();
//        Class<Person> c = Person.class;
//        //获取 somebody() 方法的Method实例
//        Method mSomeBody = c.getMethod("someBody", new Class[]{String.class, String.class});
//        //通过反射自定义注解
//        mSomeBody.invoke(person, new Object[]{"lily", "wa"});
//        iteratorAnnotations(mSomeBody);
//
//        // 获取 somebody() 方法的Method实例
//        Method mEmpty = c.getMethod("empty", new Class[]{});
//        // 执行该方法
//        mEmpty.invoke(person, new Object[]{});
//        iteratorAnnotations(mEmpty);
//    }

    public static void iteratorAnnotations(Method method) {
        if (method.isAnnotationPresent(MyAnnotationOne.class)) {
            MyAnnotationOne myAnnotation = method.getAnnotation(MyAnnotationOne.class);
            //获取该方法的MyAnnotation注解实例
            String[] values = myAnnotation.value();
            for (String str : values)
                System.out.printf(str+", ");
            System.out.println();
        }

        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(annotation);
        }
    }
}
