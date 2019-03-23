package com.noob.design.decorator;

public class TeaDecorator extends BaseTea {
    private BaseTea baseTea;

    public TeaDecorator(BaseTea baseTea){
        this.baseTea = baseTea;
    }

    @Override
    protected String getMsg() {
        return this.baseTea.getMsg();
    }

    @Override
    protected int getPrice() {
        return this.baseTea.getPrice();
    }
}
