package async;

import async.event.RecolterTaskEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log4j2
public class RetroTaskQueue {

    @Getter
    private final BlockingQueue<RecolterTaskEvent> taskQueue = new LinkedBlockingQueue<>();

    private static final RetroTaskQueue instance = new RetroTaskQueue();

    synchronized public static RetroTaskQueue getInstance() {
        return instance;
    }

    public void addTask(RecolterTaskEvent task) {
        if (!taskQueue.contains(task)) {
            log.info("Adding task {} : {}", task.getRessourceCell().id(), task);
            taskQueue.add(task);
        }
    }

    public void removeTask(RecolterTaskEvent task) {
            log.info("Removing task {} : {}", task.getRessourceCell().id(), task);
        taskQueue.remove(task);
    }

}
