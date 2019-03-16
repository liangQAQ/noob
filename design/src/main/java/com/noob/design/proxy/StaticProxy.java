package com.noob.design.proxy;

/**
 * 静态代理，指定类型，适用性不高
 */
public class StaticProxy{

    Person persion;

    public StaticProxy(Person p){
        this.persion = p;
    }

    public void buyHouse() {
        //对原有对象方法的增强
        //保护原有的对象
        before();
        persion.buyHouse();
        after();
    }

    private void before(){
        System.out.println("买前交一部分中介费");
    }
    private void after(){
        System.out.println("买后交一部分中介费");
    }
}
