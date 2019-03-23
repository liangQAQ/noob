package com.noob.design.observer.guava;

import com.google.common.eventbus.Subscribe;
import com.noob.design.observer.Question;

public class GuavaTeacher {

    private String name;

    public GuavaTeacher(String name){
        this.name = name;
    }

    @Subscribe
    public void publish(Question q){
        System.out.println(name+"收到了问题"+q.getTitle());
    }
}
