package com.noob.design.factory.abstractfactory;

public class ShanDongFactory implements Abstractfactory {
    @Override
    public IApple createApple() {
        return new ShanDongApple();
    }

    @Override
    public IBanana createBanana() {
        return new ShanDongBanana();
    }
}
