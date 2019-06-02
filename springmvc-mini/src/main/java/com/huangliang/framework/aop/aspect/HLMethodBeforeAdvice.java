package com.huangliang.framework.aop.aspect;

import com.huangliang.framework.aop.HLMethodInvocation;
import com.huangliang.framework.aop.advice.HLAdvice;
import com.huangliang.framework.aop.intercept.HLMethodInterceptor;

import java.lang.reflect.Method;

public class HLMethodBeforeAdvice extends HLAbstractAspectAdvice implements HLAdvice, HLMethodInterceptor {

    private HLJoinPoint joinPoint;

    public HLMethodBeforeAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public void before(Method method, Object[] args, Object target) throws Throwable {
        invokeAdviceMethod(this.joinPoint,null,null);
    }

    public Object invoke(HLMethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        this.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }

}
