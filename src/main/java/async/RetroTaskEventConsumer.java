package async;

import async.event.RecolterTaskEvent;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BlockingDeque;

@Log4j2
public class RetroTaskEventConsumer implements Runnable {

    private final BlockingDeque<RecolterTaskEvent> taskQueue = RetroTaskQueue.getInstance().getTaskQueue();

    @Override
    public void run() {
        while (true) {
            try {
                log.info("Waiting for a task");
                RecolterTaskEvent event = taskQueue.take();
                log.info("Processing task {} : {}", event.getRessourceCell().id(), event);
                event.execute();
            } catch (InterruptedException e) {
                log.error("Queue task has beeen interrupted", e);
            }
        }
    }

}
