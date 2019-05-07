package com.huangliang.framework.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HLBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public HLBeanWrapper(Object object){
        wrappedInstance = object;
    }

    // 返回代理以后的 Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
