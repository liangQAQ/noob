package com.huangliang.mvc.service;

import com.huangliang.framework.annotation.HLService;

@HLService
public class TestServiceImpl implements TestService {
    @Override
    public void print() {
        System.out.println("qwerty");
    }
}
