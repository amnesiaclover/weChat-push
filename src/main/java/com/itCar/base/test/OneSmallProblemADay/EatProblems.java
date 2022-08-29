package com.itCar.base.test.OneSmallProblemADay;

import lombok.SneakyThrows;

/**
 * @ClassName: EatProblems 
 * @Description: TODO 哲学家就餐问题
 * @author: liuzg
 * @Date: 2022/8/3 15:23
 * @Week: 星期三
 * @Version: v1.0
 */
public class EatProblems {

    // 筷子类
    static class Chopsticks {

    }

    // 哲学家类
    static class Philosopher extends Thread {
        private Chopsticks left, right;
        private Integer index;

        public Philosopher(String name, Integer index, Chopsticks left, Chopsticks right) {
            this.setName(name);
            this.index = index;
            this.left = left;
            this.right = right;
        }

        @SneakyThrows
        @Override
        public void run() {
            if (index % 2 == 0) {
                synchronized (right) {
                    Thread.sleep(1000 + index);
                    synchronized (left) {
                        Thread.sleep(1000);
                        System.out.println(index + "号 哲学家吃完了");
                    }
                }
            } else {
                synchronized (left) {
                    Thread.sleep(1000 + index);
                    synchronized (right) {
                        Thread.sleep(1000);
                        System.out.println(index + "号 哲学家吃完了");
                    }
                }
            }
        }
    }

    // 主线程运行main
    public static void main(String[] args) {
        Chopsticks cs0 = new Chopsticks();
        Chopsticks cs1 = new Chopsticks();
        Chopsticks cs2 = new Chopsticks();
        Chopsticks cs3 = new Chopsticks();
        Chopsticks cs4 = new Chopsticks();

        Philosopher p0 = new Philosopher("p0", 0, cs0, cs1);
        Philosopher p1 = new Philosopher("p1", 1, cs1, cs2);
        Philosopher p2 = new Philosopher("p2", 2, cs2, cs3);
        Philosopher p3 = new Philosopher("p3", 3, cs3, cs4);
        Philosopher p4 = new Philosopher("p4", 4, cs4, cs0);
        p0.start();
        p1.start();
        p2.start();
        p3.start();
        p4.start();
    }


}

