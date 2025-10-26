package com.reaksa.demo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Min threads
        executor.setMaxPoolSize(10); // Max threads
        executor.setQueueCapacity(50); // Queue size
        executor.setThreadNamePrefix("notification-");
        executor.initialize();

        return executor;
    }
}
