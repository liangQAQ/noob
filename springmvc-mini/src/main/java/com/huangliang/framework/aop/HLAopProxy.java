package com.huangliang.framework.aop;

public interface HLAopProxy {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
