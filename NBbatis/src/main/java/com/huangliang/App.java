package com.huangliang;

import com.huangliang.entity.Student;
import com.huangliang.mapper.StudentMapper;
import com.huangliang.nbbatis.NBConfiguration;
import com.huangliang.nbbatis.NBExecutor;
import com.huangliang.nbbatis.NBSqlSession;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        NBSqlSession sqlSession = new NBSqlSession(new NBConfiguration(),new NBExecutor());
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectById(1);
        System.out.println(student);
    }
}
