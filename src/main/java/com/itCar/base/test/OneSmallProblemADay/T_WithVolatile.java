package com.itCar.base.test.OneSmallProblemADay;

import org.mockito.internal.matchers.Find;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: Test1 
 * @Description: TODO 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程I添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * @author: liuzg
 * @Date: 2022/8/9 10:40
 * @Week: 星期二
 * @Version: v1.0
 */
public class T_WithVolatile {

    // volatile 使线程可见性
    volatile List list = new ArrayList();

    // synchronized
    public void add(Object o) {
        list.add(o);
    }

    // synchronized
    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T_WithVolatile t = new T_WithVolatile();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock){
                System.out.println("t2 启动");
                if (t.size() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                // 释放锁让t1继续执行
                lock.notify();
            }
        }, "t2").start();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new Thread(() -> {
            System.out.println("t1 启动");
            synchronized (lock){
                for (int i = 1; i <= 10; i++) {
                    t.add(new Object());
                    System.out.println("add:" + i);
                    if (t.size() ==5){
                        lock.notify();
                        // 释放锁 让t2继续执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();


    }
}
