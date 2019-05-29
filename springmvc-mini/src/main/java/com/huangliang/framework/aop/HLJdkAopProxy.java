package com.huangliang.framework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class HLJdkAopProxy implements HLAopProxy,InvocationHandler {

    private HLAdvisedSupport config;

    public HLJdkAopProxy(HLAdvisedSupport config){
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.config.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,classLoader.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers =
                config.getInterceptorsAndDynamicInterceptionAdvice(method,this.config.getTargetClass());
        HLMethodInvocation invocation = new
                HLMethodInvocation(proxy,
                this.config.getTarget(),
                method,
                args,
                this.config.getTargetClass(),
                interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
