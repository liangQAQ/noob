package com.huangliang.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 1.先核心线程处理，
 * 2.核心线程忙不过来了，则加入等待队列，
 * 3.等待队列满了，则非核心线程处理，
 * 4.非核心线程数也满了则执行拒绝策略。
 */
public class ThreadPoolTest {

    private static ThreadPoolExecutor service =
            new ThreadPoolExecutor(3,5,60L,
                    TimeUnit.SECONDS,new LinkedBlockingQueue());
//    private static ExecutorService fixed = Executors.newFixedThreadPool(5);
//    private static ThreadPoolExecutor cached = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//    private static ThreadPoolExecutor fixedPool = new ThreadPoolExecutor(5, 5,
//            0L, TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) throws Exception{
        int i= 1;
        while(true){
            Thread.sleep(1000);
            try {
                service.execute(new ThreadSleep(i));
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("queue size = " + service.getQueue().size());
            i++;
        }
    }
}
