package async;

import async.event.RecolterTaskEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
public class RetroTaskEventConsumer implements Runnable {

    private final PriorityBlockingQueue<RecolterTaskEvent> taskQueue = RetroTaskQueue.getInstance().getTaskQueue();

    @Override
    public void run() {
        while (true) {
            try {
                log.debug("Waiting for a task");
                RecolterTaskEvent event = taskQueue.take();
                log.debug("Processing task {} : {}", event.getRessourceCell().id(), event);
                if (event.getProcessCount() > 1) {
                    log.debug("Discarding task (too many retries) {} : {}", event.getRessourceCell().id(), event);
                } else {
                    event.execute();
                }
            } catch (InterruptedException e) {
                log.error("Queue task has beeen interrupted", e);
            }
            this.sortQueue();
        }
    }

    private void sortQueue() {
        List<RecolterTaskEvent> remainingTasks = new ArrayList<>(taskQueue);
        taskQueue.removeAll(remainingTasks);
        taskQueue.addAll(remainingTasks);
    }

}
