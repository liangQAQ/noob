package com.noob.design.singleton;

public class TestLazy {
    public static void main(String[] args) {
        Runnable a1 = new Runnable() {
            @Override
            public void run() {
                Lazy a = Lazy.getInstants();
                System.out.println(a);
            }
        };

        Thread t1 = new Thread(a1);
        Thread t2 = new Thread(a1);
        t1.start();
        t2.start();
        System.out.println("main end..");
    }
}
