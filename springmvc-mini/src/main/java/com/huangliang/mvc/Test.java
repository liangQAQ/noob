package com.huangliang.mvc;

public class Test {

    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("com.huangliang.mvc.controller.TestController");
            System.out.println(clazz.getDeclaredFields()[0].getType());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
