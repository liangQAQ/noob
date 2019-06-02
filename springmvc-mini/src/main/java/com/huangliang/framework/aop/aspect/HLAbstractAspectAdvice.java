package com.huangliang.framework.aop.aspect;

import com.huangliang.framework.aop.advice.HLAdvice;

import java.lang.reflect.Method;

/**
 * 顶层抽象类，
 * 抽象出before after afterthrow的共有成员变量
 * aspectMethod  aspectTarget
 */
public abstract class HLAbstractAspectAdvice implements HLAdvice {
    private Method aspectMethod;
    private Object aspectTarget;

    public HLAbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    protected Object invokeAdviceMethod(HLJoinPoint joinPoint,Object returnValue,Throwable ex)throws Throwable {
        Class<?> [] paramsTypes = this.aspectMethod.getParameterTypes();
        if(null == paramsTypes || paramsTypes.length == 0) {
            return this.aspectMethod.invoke(aspectTarget);
        }else {
            Object[] args = new Object[paramsTypes.length];
            for (int i = 0; i < paramsTypes.length; i++) {
                if(paramsTypes[i] == HLJoinPoint.class){
                    args[i] = joinPoint;
                }else if(paramsTypes[i] == Throwable.class){
                    args[i] = ex;
                }else if(paramsTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }
}
