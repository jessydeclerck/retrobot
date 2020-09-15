package async;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetroTaskConsumerRunner {

    private static final RetroTaskEventConsumer retroTaskEventConsumer = new RetroTaskEventConsumer();

    private static final Thread eventConsumerThread = new Thread(retroTaskEventConsumer);

    private static final RetroTaskConsumerRunner instance = new RetroTaskConsumerRunner();

    synchronized public static RetroTaskConsumerRunner getInstance() {
        return instance;
    }

    public void startEventConsumer() {
        eventConsumerThread.start();
    }

}
