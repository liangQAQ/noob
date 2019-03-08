package com.noob.design.factory.method;

import com.noob.design.factory.Apple;
import com.noob.design.factory.IFruit;

public class AppleFactory implements FactoryMethod {
    @Override
    public IFruit create() {
        return new Apple();
    }
}
