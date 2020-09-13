package async;

import lombok.extern.log4j.Log4j2;

@Log4j2
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
