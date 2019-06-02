package com.huangliang.framework.aop.aspect;

import java.lang.reflect.Method;

public class HLAfterReturningAdvice  extends HLAbstractAspectAdvice{
    public HLAfterReturningAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }
}
