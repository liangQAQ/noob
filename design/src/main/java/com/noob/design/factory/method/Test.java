package com.noob.design.factory.method;

import com.noob.design.factory.Banana;
import com.noob.design.factory.IFruit;

public class Test {
    public static void main(String[] args) {
        FactoryMethod factory = new BananaFactory();
        IFruit banana = factory.create();
        banana.eat();

        FactoryMethod factory1 = new AppFactory();
        IFruit app = factory1.create();
        app.eat();
    }
}
