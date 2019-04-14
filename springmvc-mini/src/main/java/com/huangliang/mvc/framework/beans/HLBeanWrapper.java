package com.huangliang.mvc.framework.beans;

import lombok.Data;

@Data
public class HLBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    // 返回代理以后的 Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
