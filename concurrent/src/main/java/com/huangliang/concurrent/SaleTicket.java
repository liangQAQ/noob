package com.huangliang.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 卖票
 */
public class SaleTicket {
    //总共50张票
    private static AtomicInteger count = new AtomicInteger(51);

    private static void sale() throws InterruptedException {
        //卖一张票需要1秒钟
        Thread.sleep(1000);
        if(count.get()>0){
            Thread.sleep(50);
            count.decrementAndGet();
            System.out.println("卖出一张票，剩余:"+count);
        }
    }

    public static void muti(){
        Thread sale = new Thread(new Runnable(){
            public void run() {
                try {
                    while(count.get()>0){
                        try {
                            sale();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t1 = new Thread(sale);
        Thread t2 = new Thread(sale);
        Thread t3 = new Thread(sale);
        Thread t4 = new Thread(sale);
        Thread t5 = new Thread(sale);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        while(count.get()>0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("开始卖票");
        Long start = System.currentTimeMillis();
        muti();
        System.out.println("卖完票总耗时:"+(System.currentTimeMillis()-start));
    }

    public static void single(){
        System.out.println("开始卖票");
        Long start = System.currentTimeMillis();
        while(count.get()>0){
            try {
                sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("卖完票总耗时:"+(System.currentTimeMillis()-start));
    }
}
