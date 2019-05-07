package com.huangliang.framework.beans.support;

import com.huangliang.framework.context.support.HLAbstractApplicationContext;
import com.huangliang.framework.beans.HLBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HLDefaultListableBeanFactory extends HLAbstractApplicationContext {
    //存储注册信息的 BeanDefinition
    protected final Map<String, HLBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, HLBeanDefinition>();
}
