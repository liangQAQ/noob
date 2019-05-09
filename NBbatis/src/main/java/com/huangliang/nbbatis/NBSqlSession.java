package com.huangliang.nbbatis;

public class NBSqlSession {

    private NBConfiguration configuration;
    private NBExecutor executor;

    public NBSqlSession(NBConfiguration configuration, NBExecutor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz,this);
    }

}
