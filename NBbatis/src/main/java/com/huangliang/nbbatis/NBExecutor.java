package com.huangliang.nbbatis;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class NBExecutor {

    public <T> T query(String sql, Object paramater) {
        sql = String.format(sql, paramater);
        try {
            return (T) getStateMent().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Statement getStateMent() {
        Connection conn = null;// conn用于连接数据库
        Statement stmt = null;// stmt用于发送sql语句到数据库并执行sql语句
        String connectionString = "jdbc:mysql://10.9.216.1:3306/test?user=root&password=coship&useUnicode=true&amp;characterEncoding=UTF-8";

        try {
            // 将数据驱动程序类加载到内存中
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // 通过驱动程序管理器DriverManager获取连接对象conn，conn连接的服务器和数据库信息在connectionString
            conn = DriverManager.getConnection(connectionString);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmt;
    }
}