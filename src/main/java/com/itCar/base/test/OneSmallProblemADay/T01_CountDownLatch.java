package com.itCar.base.test.OneSmallProblemADay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: T01_CountDownLatch 
 * @Description: TODO 和 WithVolatile 一样
 * @author: liuzg
 * @Date: 2022/8/9 11:26
 * @Week: 星期二
 * @Version: v1.0
 */
public class T01_CountDownLatch {

    // volatile 使线程可见性
    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T01_CountDownLatch t = new T01_CountDownLatch();

        // 门栓  需要双门栓否 程序需要枷锁
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t2 启动");
            if (t.size() != 5) {
                try {
                    // 等待
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 结束");
        }, "t2").start();

        new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 1; i <= 10; i++) {
                t.add(new Object());
                System.out.println("add:" + i);
                if (t.size() == 5) {
                    // 打开门栓，让t2执行
                    latch.countDown();
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
