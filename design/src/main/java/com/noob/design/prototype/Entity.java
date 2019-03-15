package com.noob.design.prototype;

import com.alibaba.fastjson.JSON;
import com.noob.design.singleton.Hungry;

import java.io.*;
import java.util.List;

public class Entity implements Cloneable,Serializable{

    private int age;
    private String name;
    private List<String> address;

    //浅克隆
    public Entity lowClone() {
        Entity result =  new Entity();
        result.setAddress(this.address);
        result.setName(this.name);
        result.setAge(this.age);
        return result;
    }

    //jdk浅克隆
    public Entity jdkLowClone() throws CloneNotSupportedException {
        return (Entity) this.clone();
    }

    //io深克隆
    public Entity ioClone() throws CloneNotSupportedException, IOException, ClassNotFoundException {
        //将对象写到流里
        ByteArrayOutputStream byteOut=new ByteArrayOutputStream();
        ObjectOutputStream objOut=new ObjectOutputStream(byteOut);
        objOut.writeObject(this);
        //从流里读出来
        ByteArrayInputStream byteIn=new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream objInput=new ObjectInputStream(byteIn);
        System.out.println("写文件");
        return (Entity) objInput.readObject();
    }

    //json克隆
    public Entity jsonClone(){
        String jsonStr = JSON.toJSONString(this);
        Entity h2 = JSON.toJavaObject(JSON.parseObject(jsonStr),Entity.class);
        return h2;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
}
