package com.itCar.base.config.bucket;

import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName: BucketUtil 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/6/28 15:30
 * @DAY_NAME_SHORT: 星期二
 * @Version: v1.0
 */
public class BucketUtil {

    //默认容量10
    static final int DEFAULT_MAX_COUNT = 0;
    // 默认增长速率为1
    static final int DEFAULT_CREATE_RATE = 0;
    // 使用HashMap存放令牌桶，这里默认为10个令牌桶
    public static HashMap<String, BucketUtil> buckets = new HashMap(DEFAULT_MAX_COUNT);

    //自定义容量，一旦创建不可改变
    final int maxCount;
    //自定义增长速率1s几个令牌
    int createRate;
    //当前令牌数
    static int size;


    // 默认令牌桶的容量及增长速率
    public BucketUtil() {
        maxCount = DEFAULT_MAX_COUNT;
        createRate = DEFAULT_CREATE_RATE;
    }

    // 自定义令牌桶容量及增长速率
    public BucketUtil(int maxCount, int createRate) {
        this.maxCount = maxCount;
        this.createRate = createRate;
    }

    public int getSize() {
        return size;
    }

    public boolean isFull() {
        return size == maxCount;
    }

    // 根据速率自增生成一个令牌
    public synchronized void incrTokens() {
        for (int i = 0; i < createRate; i++) {
            if (isFull()){
                System.out.println("令牌桶已满，新令牌溢出");
                return;
            }else {
                size++;
                System.out.println("当前令牌桶数量：" + size);
            }
        }
    }

//    // 根据速率自增生成一个令牌
//    public synchronized void incrTokens() {
//        for (int i = 0; i < createRate; i++) {
//            if (isFull())
//                return;
//            size++;
//        }
//    }

    // 取一个令牌
    public synchronized boolean getToken() {
        if (size > 0)
            size--;
        else
            return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        BucketUtil bucket = (BucketUtil) obj;
        if (bucket.size != size || bucket.createRate != createRate || bucket.maxCount != maxCount)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxCount, size, createRate);
    }

}
