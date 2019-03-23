package com.noob.design.observer;

public class Test {
    public static void main(String[] args) {
        Student s = new Student("小明");
        Question q = new Question();
        q.setTitle("标题名称1");
        q.setContent("正文这是为什么那？");
        s.addObserver(new Teacher("tom"));
        s.publish(q);
    }
}
