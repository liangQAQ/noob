package com.noob.design.observer;

import java.util.Observable;
import java.util.Observer;

public class Teacher implements Observer {

    private String name;

    public Teacher(String name){
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        Student s = (Student)o;
        Question q = (Question)arg;
        System.out.println(name+"老师收到了"+s.getName()+"提出的问题:"+q.getTitle());
    }
}
