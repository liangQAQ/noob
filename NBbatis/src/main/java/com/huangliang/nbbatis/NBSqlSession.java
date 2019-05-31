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

    public Object selectOne(String statementId, Object arg) {
        //通过statementId找到sql语句
        String sql = configuration.sqlMappings.getString(statementId);
        return executor.query(sql,arg);

    }
}
