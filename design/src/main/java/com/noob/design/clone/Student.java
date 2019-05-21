package com.noob.design.clone;

public class Student implements Cloneable{

    private Home home;

    private String name;

    private Integer age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Student s = new Student();
        s.setHome(new Home(this.getHome().getAddress()));
        return s;
    }

    public Student() {
    }

    public Student(String name, Integer age,Home home) {
        this.name = name;
        this.age = age;
        this.home = home;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
