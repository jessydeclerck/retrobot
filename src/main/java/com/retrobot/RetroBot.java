package com.retrobot;

import com.retrobot.network.BotServer;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@SpringBootApplication
public class RetroBot implements CommandLineRunner {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        //TODO port 80 might be used
        BotServer.init(80);
        //start(args);
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("http://localhost:4200/"));
            } catch (IOException | URISyntaxException e) {
                log.error("Error while opening UI", e);
            }
        }
    }

    public static void start(String[] args) {
        context = SpringApplication.run(RetroBot.class, args);
    }

    public static void stop() {
        context.close();
    }

    public static void restart(String[] args) {
        Thread thread = new Thread(() -> {
            log.info("App will restart");
            if(context != null) {
                context.close();
            }
            TimeUtils.sleep(1000);
            context = SpringApplication.run(RetroBot.class, args);
        });
        thread.setDaemon(false);
        thread.start();
    }

    @Override
    public void run(String... args) {
    }

}
