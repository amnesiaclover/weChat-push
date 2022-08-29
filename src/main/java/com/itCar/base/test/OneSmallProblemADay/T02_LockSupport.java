package com.itCar.base.test.OneSmallProblemADay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName: T02_LockSupport 
 * @Description: TODO 问题和 WithVolatile、CountDownLatch 一样
 * @author: liuzg
 * @Date: 2022/8/9 11:42
 * @Week: 星期二
 * @Version: v1.0
 */
public class T02_LockSupport {

    // volatile 使线程可见性
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T02_LockSupport t = new T02_LockSupport();

        // 使用LockSupport 也是有问题的，需要使用两个 t1=5时先part(),让t2执行，然后unpart(),再让t1执行

        Thread t2 = new Thread(() -> {
            System.out.println("t2 启动");
            if (t.size() != 5) {
                LockSupport.park();
            }
            System.out.println("t2 结束");
        }, "t2");
        t2.start();

        new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 1; i <= 10; i++) {
                t.add(new Object());
                System.out.println("add:" + i);
                if (t.size() == 5) {
                    LockSupport.unpark(t2);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
    }
}
