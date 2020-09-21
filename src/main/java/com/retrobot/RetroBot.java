package com.retrobot;

import com.retrobot.network.BotServer;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class RetroBot implements CommandLineRunner {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        BotServer.init(80); //TODO port 80 might be used
        context = SpringApplication.run(RetroBot.class, args);
    }

    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);
        ConfigurableApplicationContext oldContext = context;
        Thread thread = new Thread(() -> {
            log.info("App will restart");
            TimeUtils.sleep(1000);
            context = SpringApplication.run(RetroBot.class, args.getSourceArgs());
        });
        thread.setDaemon(false);
        thread.start();
        oldContext.close();
    }

    @Override
    public void run(String... args) {
    }

}
