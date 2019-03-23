package com.noob.design.observer.guava;

import com.google.common.eventbus.EventBus;
import com.noob.design.observer.Question;

public class GuavaEventTest {
    public static void main(String[] args) {
        EventBus eventbus = new EventBus();
        GuavaTeacher tom = new GuavaTeacher("tom");
        GuavaTeacher jack = new GuavaTeacher("jack");
        eventbus.register(tom);
        eventbus.register(jack);
        Question q = new Question();
        q.setTitle("title..");
        q.setContent("content..");
        eventbus.post(q);
    }
}
