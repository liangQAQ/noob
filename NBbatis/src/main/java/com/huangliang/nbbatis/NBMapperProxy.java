package com.huangliang.nbbatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class NBMapperProxy implements InvocationHandler {

    private NBSqlSession sqlSession;

    public NBMapperProxy(NBSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        return null;
    }
}
