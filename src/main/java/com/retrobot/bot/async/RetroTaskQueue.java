package com.retrobot.bot.async;

import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.async.event.TaskPriorityComparator;
import com.retrobot.bot.model.dofus.RetroDofusMap;
import com.retrobot.bot.state.CharacterState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
@Service
public class RetroTaskQueue {

    private final PriorityBlockingQueue<RecolterTaskEvent> taskQueue;

    public RetroTaskQueue(CharacterState characterState) {
        this.taskQueue = new PriorityBlockingQueue<>(10, new TaskPriorityComparator(characterState));
    }

    public RecolterTaskEvent take() throws InterruptedException {
            return taskQueue.take();
    }

    public void addTask(RecolterTaskEvent task) {
        if (!taskQueue.contains(task)) {
            log.debug("Adding task {} : {}", task.getRessourceCell().id(), task);
            taskQueue.add(task);
        }
    }

    public void removeTask(RecolterTaskEvent task) {
        log.debug("Removing task {} : {}", task.getRessourceCell().id(), task);
        taskQueue.remove(task);
    }

    public void removeMapTask(RetroDofusMap dofusMap) {
        taskQueue.removeIf(event -> event.getRessourceCell().map().equals(dofusMap));
    }

    public void removeAll() {
        taskQueue.clear();
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public void sortQueue() {
        List<RecolterTaskEvent> remainingTasks = new ArrayList<>(taskQueue);
        taskQueue.removeAll(remainingTasks);
        taskQueue.addAll(remainingTasks);
    }

}
