package com.noob.design.factory.method;

import com.noob.design.factory.Banana;
import com.noob.design.factory.IFruit;

public class BananaFactory implements FactoryMethod {
    @Override
    public IFruit create() {
        return new Banana();
    }
}
