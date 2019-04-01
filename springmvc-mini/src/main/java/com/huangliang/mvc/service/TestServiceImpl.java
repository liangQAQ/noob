package com.huangliang.mvc.service;

import com.huangliang.mvc.annotation.HLService;

@HLService
public class TestServiceImpl implements TestService {
    @Override
    public void print() {
        System.out.println("qwerty");
    }
}
