package com.huangliang.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        final Integer threadCount = 5;

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            Thread.sleep(1000);
            exec.execute(() -> {
                try {
                    System.out.println("start Thread");
                    //
                    countDownLatch.await();
                    System.out.println("Thread:" + Thread.currentThread().getName() + "---" + "date:" + System.currentTimeMillis());
                } catch (Exception e) {
//                    log.error("exception", e);
                    System.out.println(11);
                } finally {
                }
            });
            countDownLatch.countDown();
        }
//        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        System.out.println("finish");
//        exec.shutdown();
    }
}
