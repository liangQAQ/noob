package com.huangliang.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentranLockTest {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private static int count = 0;

    public static void main(String[] args) {

        for (int i = 0; i < 2; i++) {
//            lock = new ReentrantLock();
            new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        enterLock();
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(2000);
            lock.lock();
            condition.signalAll();
            lock.unlock();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count:" + count);
    }

    private static void enterLock() {
//        lock.lock();
        try {
            condition.await();
            count++;
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        lock.unlock();
    }
}
