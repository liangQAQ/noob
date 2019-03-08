package com.noob.design.factory.abstractfactory;

public class XingJiangFactory implements Abstractfactory {
    @Override
    public IApple createApple() {
        return new XingJiangApple();
    }

    @Override
    public IBanana createBanana() {
        return new XingJiangBanana();
    }
}
