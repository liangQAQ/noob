package com.noob.design.singleton;

import java.io.Serializable;

/**
 * 饿汉式单例
 * 在单例类首次加载时就创建实例
 * 缺点：浪费内存空间
 */
public class Hungry implements Serializable {

    //static 在类加载的时候初始化
    public final static Hungry hungry = new Hungry();

    private Hungry(){}

    public static Hungry getInstants(){

        return hungry;
    }

    private Object readResolve(){
        return hungry;
    }


}
