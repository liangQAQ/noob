package com.huangliang.framework.aop.intercept;

import com.huangliang.framework.aop.HLMethodInvocation;

public interface HLMethodInterceptor {
    Object invoke(HLMethodInvocation mi) throws Throwable;
}
