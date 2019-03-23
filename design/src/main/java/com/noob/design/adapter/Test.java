package com.noob.design.adapter;

public class Test {
    public static void main(String[] args) {
        NewLoginService service = new NewLoginService();

        service.login(new User());
        service.loginQQ(new User());
    }
}
