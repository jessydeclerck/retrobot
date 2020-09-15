package async;

import async.event.RecolterTaskEvent;
import async.event.TaskPriorityComparator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusMap;

import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
public class RetroTaskQueue {

    @Getter
    private final PriorityBlockingQueue<RecolterTaskEvent> taskQueue = new PriorityBlockingQueue<>(10, new TaskPriorityComparator());

    private static final RetroTaskQueue instance = new RetroTaskQueue();

    synchronized public static RetroTaskQueue getInstance() {
        return instance;
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

}
