package com.example.demo;

//字符串常量池StringTable[ "a" , "b","ab"]
public class StringTable {

    public static void main(String[] args) {
        String s1 = "a";//将“a”放入字符串常量池
        String s2 = "b";//将“b”放入字符串常量池
        String s3 = "ab";//将“ab”放入字符串常量池
        String s4 = s1 + s2;//new StringBuilder().append("s1").append("s2").toString()
        //StringBuilder的toString() -> new String() 在堆中创建了一个新的对象
        String s5 = "a" + "b";//编译器优化,直接从StringTable字符串常量池里取"ab"的值
        String s6 = s4.intern();//把s4对象尝试放入串池，如果有则不会放入，如果没有则放入串池，返回串池中的对象
        String s7 = new String("a")+new String("b")+new String("c");
        String s8 = s7.intern();
        System.out.println(s4==s3);//false
        System.out.println(s5==s3);//true
        System.out.println(s6==s3);//true
        System.out.println(s7=="abc");//true -> 串池中没有abc,s7.intern()将s7放入了串池
        System.out.println(s8=="abc");//true -> s8是返回的串池中的对象
    }

}
