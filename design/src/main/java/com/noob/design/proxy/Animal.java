package com.noob.design.proxy;

public class Animal implements EatI{
    @Override
    public void eat() {
        System.out.println("动物吃饭");
    }
}
