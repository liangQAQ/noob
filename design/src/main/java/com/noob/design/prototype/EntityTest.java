package com.noob.design.prototype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityTest {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Entity old = new Entity();
        old.setAge(1);
        old.setName("aaa");
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        old.setAddress(list);
        Entity low = old.lowClone();
        System.out.println("set属性方式浅克隆属性地址比较:"+(low.getAddress()==old.getAddress()));
        Entity jdkLowClone = old.jdkLowClone();
        System.out.println("jdk浅克隆属性地址比较:"+(jdkLowClone.getAddress()==old.getAddress()));
        Entity fileClone = old.ioClone();
        System.out.println("输入输出流方式深克隆属性地址比较:"+(fileClone.getAddress()==old.getAddress()));
        Entity jsonClone = old.jsonClone();
        System.out.println("json序列化方式深克隆属性地址比较:"+(jsonClone.getAddress()==old.getAddress()));

    }

}
