package com.noob.design.decorator;

public class SugerDecorator extends TeaDecorator{

    public SugerDecorator(BaseTea baseTea) {
        super(baseTea);
    }

    @Override
    protected String getMsg() {
        return super.getMsg()+"+一份糖";
    }

    @Override
    protected int getPrice() {
        return super.getPrice()+1;
    }
}
