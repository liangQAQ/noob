package com.huangliang.concurrent;

public class ThreadSleep implements Runnable {

    private Integer number;

    public ThreadSleep(Integer number){
        this.number = number;
    }

    @Override
    public void run() {
        sleep();
    }

    private void sleep(){
        //睡眠10秒钟，每一秒输出一次
        for(int i=1 ;i<=20 ; i++){
            System.out.println("number:"+number+" , sleep:"+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
