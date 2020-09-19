package com.retrobot;

import com.retrobot.utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RetroBot implements CommandLineRunner {

    public static void main(String[] args) {
        NativeWindowsEvents.prepareForAutomation("Carlatorium - Dofus Retro v1.33.0");
        SpringApplication.run(RetroBot.class, args);
    }

    @Override
    public void run(String... args) {
    }

}
