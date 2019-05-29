package com.huangliang.framework.aop;

import java.lang.reflect.Method;
import java.util.List;

public class HLMethodInvocation {

    public HLMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                              Class<?> targetClass, List<Object>
                                      interceptorsAndDynamicMethodMatchers) {
//        this.proxy = proxy;
//        this.target = target;
//        this.targetClass = targetClass;
//        this.method = method;
//        this.arguments = arguments;
//        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Exception{
        return null;
    }
}
