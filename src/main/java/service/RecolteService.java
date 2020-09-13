package service;

import automation.NativeWindowsEvents;
import lombok.extern.log4j.Log4j2;
import utils.TimeUtils;

import static automation.PixelConstants.OFFSET_INTERACTION_X;
import static automation.PixelConstants.OFFSET_INTERACTION_Y;

@Log4j2
public class RecolteService {

    private static final RecolteService instance = new RecolteService();

    synchronized public static RecolteService getInstance() {
        return instance;
    }

    public void recolterRessource(int windowRelativeX, int windowRelativeY) {
        NativeWindowsEvents.clic(windowRelativeX, windowRelativeY);
        TimeUtils.sleep(200);
        NativeWindowsEvents.clic(windowRelativeX + OFFSET_INTERACTION_X, windowRelativeY + OFFSET_INTERACTION_Y);
    }
}
