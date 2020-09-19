package com.retrobot.bot.service;

import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final RetroTaskQueue retroTaskQueue;

    public TaskService(RetroTaskQueue retroTaskQueue) {
        this.retroTaskQueue = retroTaskQueue;
    }

    public void queueTaskRecolte(RetroRessourceCell ressourceCell) {
        retroTaskQueue.addTask(new RecolterTaskEvent(ressourceCell));
    }

    public void removeQueueTask(RetroRessourceCell unavailableRessourceCell) {
        retroTaskQueue.removeTask(new RecolterTaskEvent(unavailableRessourceCell));
    }

    public void removeMapTask(RetroDofusMap map) {
        retroTaskQueue.removeMapTask(map);
    }
}
