package com.thinkive.common.authority.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Describe 配置异步的线程池
 * @auther AN dengchangneng
 * @create 2017/11/14
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    /**
     * @Describe 创建线程池
     * @return
     */
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * @Describe 配置异步的线程池，以及超时时间
     * @param asyncSupportConfigurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {
        asyncSupportConfigurer.setTaskExecutor(getAsyncExecutor());
        asyncSupportConfigurer.setDefaultTimeout(1000);
    }
}
