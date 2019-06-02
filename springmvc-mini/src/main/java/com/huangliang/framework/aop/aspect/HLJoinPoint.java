package com.huangliang.framework.aop.aspect;

import java.lang.reflect.Method;

public interface HLJoinPoint {

    Method getMethod();

    Object[] getArguments();

    Object getThis();
}
