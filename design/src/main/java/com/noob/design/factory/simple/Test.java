package com.noob.design.factory.simple;

import com.noob.design.factory.Apple;
import com.noob.design.factory.IFruit;

public class Test {
    public static void main(String[] args) {
        SimpleFactory factory = new SimpleFactory();
        IFruit app = factory.create(Apple.class);
        app.eat();
    }
}
