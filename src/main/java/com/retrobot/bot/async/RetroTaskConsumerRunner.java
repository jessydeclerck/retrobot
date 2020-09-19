package com.retrobot.bot.async;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetroTaskConsumerRunner {

    private final RetroTaskEventConsumer retroTaskEventConsumer;

    public RetroTaskConsumerRunner(RetroTaskEventConsumer retroTaskEventConsumer) {
        this.retroTaskEventConsumer = retroTaskEventConsumer;
    }

    public void startEventConsumer() {
        new Thread(retroTaskEventConsumer).start();
    }

}
