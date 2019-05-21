package com.noob.design.clone;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) {
        Student student = new Student("jj",77,new Home("11"));
        Student student1 = new Student();
        try {
            BeanUtils.copyProperties(student1,student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(student);
        System.out.println(student1);

        System.out.println(student.getHome());
        System.out.println(student1.getHome());
        student.getHome().setAddress("ssss");
        System.out.println(student.getHome().getAddress());
        System.out.println(student1.getHome().getAddress());
    }
}
