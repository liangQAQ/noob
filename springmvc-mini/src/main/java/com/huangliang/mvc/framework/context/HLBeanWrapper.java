package com.huangliang.mvc.framework.context;

import lombok.Data;

@Data
public class HLBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;
}
