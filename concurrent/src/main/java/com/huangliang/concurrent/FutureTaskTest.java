package com.huangliang.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask ft = new FutureTask(new call());
        new Thread(ft).start();
        System.out.println(ft.get());
    }

    static class call implements Callable {
        public String call() throws Exception {
            Thread.sleep(3000);
            return "calllll";
        }
    }
}
