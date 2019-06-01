package com.huangliang.nbbatis;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

public class NBConfiguration {

    public static final ResourceBundle sqlMappings ;

    static{
        sqlMappings = ResourceBundle.getBundle("mesql");
    }

    public <T> T getMapper(Class clazz,NBSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),new Class[]{clazz},new NBMapperProxy(sqlSession));
    }

}
