package com.huangliang.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ShutDownTest {

    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {
        Thread wait = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while(i<=5){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("i="+i);
                }
            }
        });

        service.execute(wait);
        Thread.sleep(1000);
        service.shutdown();
    }
}
