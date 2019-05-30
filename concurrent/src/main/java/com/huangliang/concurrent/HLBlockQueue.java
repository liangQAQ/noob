package com.huangliang.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自己实现的阻塞队列
 * 当队列为空时，请求take会被阻塞，直到队列不为空
 * 当队列满了以后，存储元素的线程需要备阻塞直到队列可以添加数据
 */
public class HLBlockQueue<T> {

    private List<T> list = new ArrayList();

    private int queueSize = 0;

    //锁
    private ReentrantLock lock = new ReentrantLock();

    private Condition takeCondition = lock.newCondition();
    private Condition putCondition = lock.newCondition();

    public HLBlockQueue(int queueSize) {
        this.queueSize = queueSize;
    }

    public int size() {
        return list.size();
    }

    public boolean put(T t) {
        boolean result = false;
        try {
            lock.lock();
            if (list.size() >= queueSize) {
                //阻塞线程
                System.out.println(Thread.currentThread().getName()+":put await...");
                putCondition.await();
                System.out.println(Thread.currentThread().getName()+":put out of await...");
                return put(t);
            }
            result = list.add(t);
            takeCondition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    public T take() {
        try {
            lock.lock();
            if (list.size() > 0) {
                //有就直接出列
                T t = list.remove(0);
                putCondition.signal();
                return t;
            }
            //没有就阻塞
            System.out.println(Thread.currentThread().getName()+":take await...");
            takeCondition.await();
            System.out.println(Thread.currentThread().getName()+":take out of await...");
            return take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }finally {
            lock.unlock();
        }
    }
}
