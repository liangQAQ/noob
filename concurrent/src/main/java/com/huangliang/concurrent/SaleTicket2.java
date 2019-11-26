package com.huangliang.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class SaleTicket2 {
    static AtomicInteger count = new AtomicInteger(51);
    static AtomicInteger soldCount = new AtomicInteger(0);

    public static void sell() {
        if (count.get() > 0) {
            try {
                System.out.println("sss");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int tmp;
            if ((tmp = count.get()) > 0 && count.compareAndSet(tmp, tmp - 1)) {
                soldCount.getAndAdd(1);

                System.out.println("剩余票" + count.get());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    while (count.get() > 0) {
                        sell();
                    }
                }
            }).start();
        }
        Thread.sleep(60000);
        System.out.println("result:" + count);
        System.out.println("sold count" + soldCount);
    }
}
