package com.itCar.base.test.OneSmallProblemADay;

import lombok.SneakyThrows;

import java.util.LinkedList;

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
public class MyContainer1<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    @SneakyThrows
    public synchronized void put(T t) {
        while (lists.size() == MAX) {
            this.wait();
        }
        lists.add(t);
        ++count;
        // 通知消费者线程进行消费
        this.notifyAll();
    }

    @SneakyThrows
    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            this.wait();
        }
        t = lists.removeFirst();
        count--;
        // 通知生产者进行生产
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> t1 = new MyContainer1<>();

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
