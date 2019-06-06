package com.huangliang.concurrent;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 模拟一个厨子炒菜，服务员上菜的场景，
 * 厨子没炒出来服务员就一直阻塞等待
 */
public class BlockQueueDemo {

    public static LinkedBlockingQueue<String> food = new LinkedBlockingQueue<String>();

    public static String[] foodArray = new String[]{"青椒肉丝","香干肉丝","榨菜肉丝","茄子肉沫"};

    static class Waiter implements Runnable{
        public void run() {
            try {
                while(true){
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+"取走了:"+food.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class Worker implements Runnable{
        public void run() {
            try {
                while (true){
                    String foods = foodArray[new Random().nextInt(4)];
                    food.put(foods);
                    System.out.println(Thread.currentThread().getName()+"做出了:"+foods);
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("start...");
        new Thread(new Waiter()).start();
        new Thread(new Waiter()).start();
        new Thread(new Worker()).start();
    }
}
