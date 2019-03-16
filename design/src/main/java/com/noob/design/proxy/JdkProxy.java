package com.noob.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理根据传入对象所实现的接口生成了一个实现该接口的新的类
 */
public class JdkProxy implements InvocationHandler {

    Object object;

    public Object getInstance(Object target) throws Exception {
        this.object = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(this.object,args);
        after();
        return object;
    }

    private void before(){
        System.out.println("before..");
    }

    private void after(){
        System.out.println("after..");
    }
}
