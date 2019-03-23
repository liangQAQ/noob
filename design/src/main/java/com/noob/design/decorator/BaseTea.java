package com.noob.design.decorator;

public class BaseTea extends Tea{
    @Override
    protected String getMsg() {
        return "普通奶茶";
    }

    @Override
    protected int getPrice() {
        return 5;
    }
}
