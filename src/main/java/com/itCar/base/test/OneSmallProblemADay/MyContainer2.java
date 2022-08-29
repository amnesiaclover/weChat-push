package com.itCar.base.test.OneSmallProblemADay;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: MyContainer1 
 * @Description: TODO 写一个固定容量同步容器，
 *                拥有put和get方法，以及getCount方法，
 *                能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * @author: liuzg
 * @Date: 2022/8/9 12:58
 * @Week: 星期二
 * @Version: v1.0
 */
public class MyContainer2<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    // ReentrantLock 和 synchronized 的本质之一 是 R 可以创建多个不同的线程等待队列
    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                producer.await();
            }
            lists.add(t);
            ++count;
            // 通知消费者线程进行消费
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.await();
            }
            t = lists.removeFirst();
            count--;
            // 通知生产者进行生产
            producer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {
        MyContainer2<String> t1 = new MyContainer2<>();

        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(t1.get());
                }
            }, "c" + i).start();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    t1.put(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i).start();
        }

    }
}
