package com.noob.design.factory;

import com.noob.design.factory.IFruit;

public class Banana implements IFruit{
    @Override
    public void eat() {
        System.out.println("eat banana...");
    }
}
