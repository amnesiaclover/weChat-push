package com.itCar.base.test.OneSmallProblemADay;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: ProducerAndConsumerByLock 
 * @Description: TODO 生产者消费者问题
 * @author: liuzg
 * @Date: 2022/8/4 10:58
 * @Week: 星期四
 * @Version: v1.0
 */
public class ProducerAndConsumerByLock {
    private static int count = 0;
    private static int maxNum = 3;

    // 这里使用ReentrantLock 不用synchronized是因为ReentrantLock可以创建多个队列
    ReentrantLock lock = new ReentrantLock();
    // 生产者对列
    Condition producerCondition = lock.newCondition();
    // 消费者对列
    Condition consumerCondition = lock.newCondition();

    public static void main(String[] args) {
        ProducerAndConsumerByLock test = new ProducerAndConsumerByLock();

        new Thread(test.new Producer()).start();
        new Thread(test.new Producer()).start();

        new Thread(test.new Consumer()).start();
        new Thread(test.new Consumer()).start();
    }

    class Producer extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(500);
                // 获取锁
                lock.lock();
                try {
                    while (count >= maxNum){
                        producerCondition.await();
                        System.out.println("生产者能力达到上限，进入等待状态");
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
                    // 唤醒消费者
                    consumerCondition.signal();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    // 释放锁
                    lock.unlock();
                }
            }
        }
    }

    class Consumer extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(700);
                lock.lock();
                try {
                    while (count <= 0){
                        consumerCondition.await();
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + "消费者消费 目前总共有" + count);
                    // 唤醒生产者
                    producerCondition.signal();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
