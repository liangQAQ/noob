package com.huangliang.concurrent;

import java.util.Random;

public class HLBlockQueueTest {
    public static void main(String[] args) {
        final HLBlockQueue<String> queue = new HLBlockQueue(10);
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        System.out.println("take0:"+queue.take()+"====size:"+queue.size());
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        System.out.println("take1:"+queue.take()+"====size:"+queue.size());
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                        int num =new Random().nextInt(5000);
                        System.out.println("put success:"+num+":"+queue.put( num+"")+"====size:"+queue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            }).start();
    }
}
