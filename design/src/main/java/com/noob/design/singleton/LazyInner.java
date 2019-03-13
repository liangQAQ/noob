package com.noob.design.singleton;

//这种形式兼顾饿汉式的内存浪费，也兼顾 synchronized 性能问题
//完美地屏蔽了这两个缺点
public class LazyInner {

    static{
        System.out.println("LazyInner static执行了");
    }

    //如果没使用的话，内部类是不加载的
    private LazyInner(){
        System.out.println("私有构造方法执行了");
    }

    public static LazyInner getInstants(){
        return Inner.Lazy;
    }

    public static void out() {
        System.out.println("随便调用了一个方法");
    }

    private static class Inner{
        static {
            System.out.println("Inner static执行了");
        }
        private static final LazyInner Lazy = new LazyInner();
    }

}
