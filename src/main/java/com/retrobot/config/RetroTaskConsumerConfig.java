package com.retrobot.config;

import com.retrobot.bot.async.RetroTaskConsumerRunner;
import com.retrobot.bot.async.RetroTaskEventConsumer;
import com.retrobot.bot.async.RetroTaskEventExecutor;
import com.retrobot.bot.async.RetroTaskQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RetroTaskConsumerConfig {

    private final RetroTaskEventExecutor retroTaskEventExecutor;
    private final RetroTaskQueue retroTaskQueue;

    public RetroTaskConsumerConfig(RetroTaskEventExecutor retroTaskEventExecutor, RetroTaskQueue retroTaskQueue) {
        this.retroTaskQueue = retroTaskQueue;
        this.retroTaskEventExecutor = retroTaskEventExecutor;
    }

    @Bean
    RetroTaskEventConsumer retroTaskEventConsumer() {
        return new RetroTaskEventConsumer(retroTaskQueue, retroTaskEventExecutor);
    }

    @Bean
    RetroTaskConsumerRunner retroTaskConsumerRunner() {
        return new RetroTaskConsumerRunner(retroTaskEventConsumer());
    }

    @PostConstruct
    public void init() {
        retroTaskConsumerRunner().startEventConsumer();
    }

}
