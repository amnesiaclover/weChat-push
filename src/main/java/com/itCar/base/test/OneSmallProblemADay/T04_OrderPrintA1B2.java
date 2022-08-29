package com.itCar.base.test.OneSmallProblemADay;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName: T04_OrderPrintA1B2 
 * @Description: TODO 顺序执行 A1B2C3D4
 * @author: liuzg
 * @Date: 2022/8/9 12:40
 * @Week: 星期二
 * @Version: v1.0
 */
public class T04_OrderPrintA1B2 {

    String letter[] = {"A","B","C","D","E","F","G","H","I"};
    int number[] = {1,2,3,4,5,6,7,8,9};

    static Thread t1 = null, t2 = null;


    public static void main(String[] args) {
        T04_OrderPrintA1B2 t = new T04_OrderPrintA1B2();

        t1 = new Thread(()->{
            for (int i = 0; i < t.letter.length; i++) {
                System.out.print(t.letter[i]);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
            LockSupport.unpark(t2);
        }, "t1");

        t2 = new Thread(()->{
            LockSupport.park();
            for (int i : t.number) {
                System.out.print(i);
                LockSupport.unpark(t1);
                LockSupport.park();

            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
