package com.itCar.base.config.cors.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @ClassName: AsyncConfig 
 * @Description: TODO 线程池的配置
 * @author: liuzg
 * @Date: 2021/5/14 14:49
 * @Version: v1.0
 */
@Configuration
public class AsyncConfig {

    private static final int MAX_POOL_SIZE = 50;

    private static final int CORE_POOL_SIZE = 20;

    @Bean("asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);        // 配置核心线程数
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);      // 配置最大线程数
        asyncTaskExecutor.setThreadNamePrefix("async-thread-"); // 配置线程池中的线程的名称前缀
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}
