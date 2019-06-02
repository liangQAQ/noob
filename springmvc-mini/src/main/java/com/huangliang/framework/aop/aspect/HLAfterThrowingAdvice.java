package com.huangliang.framework.aop.aspect;

import java.lang.reflect.Method;

public class HLAfterThrowingAdvice extends HLAbstractAspectAdvice{

    private String throwingName;

    public HLAfterThrowingAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public String getThrowingName() {
        return throwingName;
    }

    public void setThrowingName(String throwingName) {
        this.throwingName = throwingName;
    }
}
