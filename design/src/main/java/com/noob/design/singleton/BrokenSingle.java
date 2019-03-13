package com.noob.design.singleton;

import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BrokenSingle {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

//        json();
        io();
    }

    public static void json(){
        System.out.println("json序列化破坏单例");
        Hungry h1 = Hungry.getInstants();
        String jsonStr = JSON.toJSONString(h1);
        Hungry h2 = JSON.toJavaObject(JSON.parseObject(jsonStr),Hungry.class);
        System.out.println(h1);
        System.out.println(h2);
    }

    public static void io(){
        System.out.println("文件序列化破坏单例");
        Hungry s1 = null;
        Hungry s2 = Hungry.getInstants();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("1.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();
            FileInputStream fis = new FileInputStream("1.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (Hungry)ois.readObject();
            ois.close();
            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
