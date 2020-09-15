package utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeUtils {

    public static void sleep(int ms) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

}
