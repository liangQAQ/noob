package com.huangliang.mvc.aspect;

public class LogAspect {

    public void before() {
        System.out.println("调用前");
    }

    public void after() {
        System.out.println("调用后");
    }

    public void afterThrowing() {

    }
}
