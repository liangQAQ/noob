package com.noob.design.proxy;

public class ProxyTest {

    public static void main(String[] args) throws Exception {
//        new StaticProxy(new PersionA()).buyHouse();
        new StaticProxy(new PersonA()).buyHouse();

        ((Person)new JdkProxy().getInstance(new PersonA())).buyHouse();
        ((Person)new JdkProxy().getInstance(new PersonA())).findJob();
        ((EatI)new JdkProxy().getInstance(new Animal())).eat();

    }

}
