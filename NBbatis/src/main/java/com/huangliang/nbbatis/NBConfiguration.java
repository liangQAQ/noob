package com.huangliang.nbbatis;

import java.lang.reflect.Proxy;

public class NBConfiguration {

    public <T> T getMapper(Class clazz,NBSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getClass().getInterfaces(),new NBMapperProxy(sqlSession));
    }

}
