package service;

import async.RetroTaskQueue;
import async.event.RecolterTaskEvent;
import automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroRessourceCell;
import state.CharacterState;
import state.MapState;
import utils.TimeUtils;

import static automation.PixelConstants.OFFSET_INTERACTION_X;
import static automation.PixelConstants.OFFSET_INTERACTION_Y;

@Slf4j
public class RecolteService {

    private static final RecolteService instance = new RecolteService();
    private final CharacterState characterState = CharacterState.getInstance();
    private final MapState mapState = MapState.getInstance();
    private final RetroTaskQueue retroTaskQueue = RetroTaskQueue.getInstance();
    private final static int OFFSET_MILIEU_CASE_X = 20, OFFSET_MILIEU_CASE_Y = 20;

    synchronized public static RecolteService getInstance() {
        return instance;
    }

    public void recolterRessource(RetroRessourceCell ressourceCell) {
        log.info("Recolte de la ressource {}", ressourceCell.id());
        TimeUtils.sleep(200);
        double x = ressourceCell.getWindowRelativeX() + OFFSET_MILIEU_CASE_X;
        double y = ressourceCell.getWindowRelativeY() - OFFSET_MILIEU_CASE_Y;
        NativeWindowsEvents.clic(x, y);//on evite de cliquer au milieu de la case todo refacto
        TimeUtils.sleep(500);
        NativeWindowsEvents.clic(x + OFFSET_INTERACTION_X, y + OFFSET_INTERACTION_Y);
        TimeUtils.sleep(1000);
        RetroRessourceCell targetedRessourceCell = characterState.getCurrentGatheringTarget();
        if (targetedRessourceCell != null && !ressourceCell.equals(targetedRessourceCell)) {
            log.info("La ressource ciblée est différente de celle prévue : {} vs {}", ressourceCell.id(), targetedRessourceCell.id());
            if (mapState.getAvailableRessources().containsKey(ressourceCell.id())) {
                retroTaskQueue.addTask(new RecolterTaskEvent(ressourceCell));
            }
        }
        characterState.setCurrentGatheringTarget(null);
    }
}
