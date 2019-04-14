package com.huangliang.mvc.framework.context.support;

/**
 * IOC 容器实现的顶层设计
 */
public abstract class HLAbstractApplicationContext {
    //受保护，只提供给子类重写
    public void refresh() throws Exception {}
}
