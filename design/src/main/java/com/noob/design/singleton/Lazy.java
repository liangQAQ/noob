package com.noob.design.singleton;

/**
 * 懒汉式单例：当被使用到的时候加载
 */
public class Lazy {

    private static Lazy lazy = null;

    private Lazy(){
        System.out.println("私有构造方法调用了");
    }

    //synchronized为啥不加在方法上
    //当单例实例被创建以后，其后的请求没有必要再使用互斥机制了,
    public static Lazy getInstants(){
        if(lazy == null){
            synchronized(Lazy.class){
                if(lazy == null){
                    lazy = new Lazy();
                }
            }
        }
        return lazy;
    }
}
