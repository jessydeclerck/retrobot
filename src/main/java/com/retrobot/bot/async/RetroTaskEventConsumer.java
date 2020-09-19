package com.retrobot.bot.async;

import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.service.DeplacementService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetroTaskEventConsumer implements Runnable {

    private final RetroTaskEventExecutor retroTaskEventExecutor;
    private final RetroTaskQueue retroTaskQueue;
    private final DeplacementService deplacementService;

    public RetroTaskEventConsumer(RetroTaskQueue retroTaskQueue, RetroTaskEventExecutor retroTaskEventExecutor, DeplacementService deplacementService) {
        this.retroTaskEventExecutor = retroTaskEventExecutor;
        this.retroTaskQueue = retroTaskQueue;
        this.deplacementService = deplacementService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                retroTaskQueue.sortQueue();
                log.debug("Waiting for a task");
                RecolterTaskEvent event = retroTaskQueue.getTaskQueue().take();
                log.debug("Processing task {} : {}", event.getRessourceCell().id(), event);
                if (event.getProcessCount() > 1) {
                    log.info("Discarding task (too many retries) {} : {}", event.getRessourceCell().id(), event);
                } else {
                    retroTaskEventExecutor.execute(event);
                }
            } catch (InterruptedException e) {
                log.error("Queue task has beeen interrupted", e);
            }
            if (retroTaskQueue.isEmpty()) {
                deplacementService.goNextGatherMap();
            }
        }
    }

}
