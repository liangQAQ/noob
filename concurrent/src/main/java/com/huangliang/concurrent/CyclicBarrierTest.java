package com.huangliang.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        final Integer threadCount = 5;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);

        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < 7; i++) {
            if (i == 2) {
                cyclicBarrier.reset();
            }
            exec.execute(() -> {
                try {
                    System.out.println("start Thread,cyclicBarrier await");
                    cyclicBarrier.await();
                    System.out.println("Thread:" + Thread.currentThread().getName() + "---" + "date:" + System.currentTimeMillis());
                } catch (Exception e) {
                    System.out.println(11);
                } finally {
                }
            });
        }
        System.out.println("finish");
    }
}
