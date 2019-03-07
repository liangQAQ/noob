package com.noob.design.factory.abstractfactory;

public class Banana implements IFruit {
    @Override
    public void eat() {
        System.out.println("eat banana...");
    }

    @Override
    public void drop() {
        System.out.println("drop banana..");
    }
}
