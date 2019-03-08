package com.noob.design.factory.abstractfactory;

public class Test {

    public static void main(String[] args) {
        Abstractfactory shanDongFactory = new ShanDongFactory();
        shanDongFactory.createApple().eat();
        shanDongFactory.createBanana().eat();

        Abstractfactory  xingJiangFactory = new XingJiangFactory();
        xingJiangFactory.createApple().eat();
        xingJiangFactory.createBanana().eat();
    }

}
