package com.huangliang.concurrent;

public class ThreadTest {
    public static void main(String[] args) {

        Integer a = 1;
        Integer b = 3;
        double c = (double) Math.round(((double)a/b) * 100) / 100;

        System.out.println(c);
    }
}
