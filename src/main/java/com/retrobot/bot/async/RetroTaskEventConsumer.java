package com.retrobot.bot.async;

import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.service.BotService;
import com.retrobot.bot.service.DeplacementService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetroTaskEventConsumer implements Runnable {

    private final RetroTaskEventExecutor retroTaskEventExecutor;
    private final RetroTaskQueue retroTaskQueue;
    private final DeplacementService deplacementService;
    private final BotService botService;

    public RetroTaskEventConsumer(RetroTaskQueue retroTaskQueue, RetroTaskEventExecutor retroTaskEventExecutor, DeplacementService deplacementService, BotService botService) {
        this.retroTaskEventExecutor = retroTaskEventExecutor;
        this.retroTaskQueue = retroTaskQueue;
        this.deplacementService = deplacementService;
        this.botService = botService;
    }

    @Override
    public void run() {
        while (true) {
            retroTaskQueue.sortQueue();
            log.debug("Waiting for a task");
            RecolterTaskEvent event = retroTaskQueue.take();
            log.debug("Processing task {} : {}", event.getRessourceCell().id(), event);
            if (event.getProcessCount() > 1) {
                log.info("Discarding task (too many retries) {} : {}", event.getRessourceCell().id(), event);
                botService.setUnavailableRessource(event.getRessourceCell().id());
            } else {
                retroTaskEventExecutor.execute(event);
            }
            if (retroTaskQueue.isEmpty()) {
                deplacementService.goNextGatherMap();
            }
        }
    }

}
