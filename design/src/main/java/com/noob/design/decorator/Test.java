package com.noob.design.decorator;

public class Test {
    public static void main(String[] args) {
        BaseTea t = new BaseTea();
        t = new SugerDecorator(t);
        t = new SugerDecorator(t);
        t = new SugerDecorator(t);
        t = new SugerDecorator(t);
        System.out.println(t.getMsg());
        System.out.println("总价s"+t.getPrice());
    }
}
