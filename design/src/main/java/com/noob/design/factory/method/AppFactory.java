package com.noob.design.factory.method;

import com.noob.design.factory.App;
import com.noob.design.factory.IFruit;

public class AppFactory implements FactoryMethod {
    @Override
    public IFruit create() {
        return new App();
    }
}
