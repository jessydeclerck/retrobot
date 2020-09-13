package utils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TimeUtils {

    public static void sleep(int ms){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

}
