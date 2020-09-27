package com.retrobot.config;

import com.retrobot.bot.async.RetroTaskEventConsumer;
import com.retrobot.bot.async.RetroTaskEventExecutor;
import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.service.BotService;
import com.retrobot.bot.service.DeplacementService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;

@Configuration
public class RetroTaskConsumerConfig {

    private final RetroTaskEventExecutor retroTaskEventExecutor;
    private final RetroTaskQueue retroTaskQueue;
    private final DeplacementService deplacementService;
    private final BotService botService;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public RetroTaskConsumerConfig(RetroTaskEventExecutor retroTaskEventExecutor, RetroTaskQueue retroTaskQueue, DeplacementService deplacementService, BotService botService, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.retroTaskQueue = retroTaskQueue;
        this.retroTaskEventExecutor = retroTaskEventExecutor;
        this.deplacementService = deplacementService;
        this.botService = botService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Bean
    RetroTaskEventConsumer retroTaskEventConsumer() {
        return new RetroTaskEventConsumer(retroTaskQueue, retroTaskEventExecutor, deplacementService, botService);
    }

    @PostConstruct
    public void init() {
        threadPoolTaskExecutor.execute(retroTaskEventConsumer());
    }

}
