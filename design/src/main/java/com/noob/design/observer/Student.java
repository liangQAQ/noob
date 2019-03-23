package com.noob.design.observer;

import java.util.Observable;

public class Student extends Observable {

    private String name;

    public Student(String name){
        this.name = name;
    }

    public void publish(Question question){
        System.out.println("学生"+name+"发布了一个问题"+question.getTitle());
        setChanged();
        notifyObservers(question);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
