package com.huangliang.framework.core;

public interface HLFactoryBean {
    /**
     * 根据 beanName 从 IOC 容器之中获得一个实例 Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
