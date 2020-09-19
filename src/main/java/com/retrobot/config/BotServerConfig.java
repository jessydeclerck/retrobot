package com.retrobot.config;

import com.retrobot.network.BotServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotServerConfig {

    @Bean
    public BotServer botServer() {
        return new BotServer(80);
    }

}
