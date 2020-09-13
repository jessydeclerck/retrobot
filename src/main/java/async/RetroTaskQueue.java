package async;

import async.event.RecolterTaskEvent;
import lombok.Getter;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class RetroTaskQueue {

    @Getter
    private final BlockingDeque<RecolterTaskEvent> taskQueue = new LinkedBlockingDeque<>();

    private static final RetroTaskQueue instance = new RetroTaskQueue();

    synchronized public static RetroTaskQueue getInstance() {
        return instance;
    }

    public void addTask(RecolterTaskEvent task) {
        taskQueue.add(task);
    }

}
