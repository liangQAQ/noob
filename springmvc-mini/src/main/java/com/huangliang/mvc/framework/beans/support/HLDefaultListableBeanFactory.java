package com.huangliang.mvc.framework.beans.support;

import com.huangliang.mvc.framework.beans.HLBeanDefinition;
import com.huangliang.mvc.framework.context.support.HLAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HLDefaultListableBeanFactory extends HLAbstractApplicationContext {
    //存储注册信息的 BeanDefinition
    protected final Map<String, HLBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, HLBeanDefinition>();
}
