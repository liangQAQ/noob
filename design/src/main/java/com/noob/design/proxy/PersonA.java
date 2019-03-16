package com.noob.design.proxy;

public class PersonA implements Person {
    @Override
    public void buyHouse() {
        System.out.println("我去买房子");
    }

    @Override
    public void findJob() {
        System.out.println("找工作了");
    }
}
