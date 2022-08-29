package com.itCar.base.test.OneSmallProblemADay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName: T02_LockSupport 
 * @Description: TODO 问题和 WithVolatile、CountDownLatch 一样
 * @author: liuzg
 * @Date: 2022/8/9 11:42
 * @Week: 星期二
 * @Version: v1.0
 */
public class T03_LockSupport_WithoutSleep {

    // volatile 使线程可见性
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    static Thread t1 = null, t2 = null;

    /**
     * 讲解：t2先执行，但是呢，t2中判断!=5就一直等待，等到t1执行到=5时，t1叫醒t2，然后自己开始
     * 等待，等待t2执行完成后，t1再继续执行
     * @param args
     */
    public static void main(String[] args) {
        T03_LockSupport_WithoutSleep t = new T03_LockSupport_WithoutSleep();

        // 使用LockSupport 也是有问题的，需要使用两个 t1=5时先part(),让t2执行，然后unpart(),再让t1执行
        t2 = new Thread(() -> {
            System.out.println("t2 启动");
            // 也可不用判断，线程开始就先part(),等待t1叫醒自己
//            if (t.size() != 5) {
                // 等待
                LockSupport.park();
//            }
            System.out.println("t2 结束");
            // 执行完成后，叫醒t1，可以执行了
            LockSupport.unpark(t1);
        }, "t2");


        t1 = new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 1; i <= 10; i++) {
                t.add(new Object());
                System.out.println("add:" + i);
                if (t.size() == 5) {
                    // t1 == 5时，叫醒t2执行
                    LockSupport.unpark(t2);
                    // 然后自己进入等待，等待t2执行完叫自己
                    LockSupport.park();
                }
            }
        }, "t1");

        t2.start();
        t1.start();
    }
}
