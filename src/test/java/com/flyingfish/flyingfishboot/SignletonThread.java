package com.flyingfish.flyingfishboot;

public class SignletonThread {
    //1. 私有的静态的对象
    private static SignletonThread signletonThread;

    //2. 私有的构造方法
    private SignletonThread(){}

    //3. 公共的静态的实例方法 在if里面加上锁synchronized
    public static SignletonThread getInstance(){
        //if (signletonThread==null){
            synchronized (SignletonThread.class){
                if (signletonThread==null){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    signletonThread=new SignletonThread();
                }
            }
        //}
        return signletonThread;
    }

    //测试方法
    public static void main(String[] args) {
        //利用for循环 模拟多线程环境调用
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                //看每次获取对象的hashcode是否一致 判断是否获取了同一个对象
                System.out.println("获取的hashCode是： "+SignletonThread.getInstance().hashCode());
            }).start();
        }
    }

}
