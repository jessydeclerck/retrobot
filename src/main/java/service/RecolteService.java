package service;

import async.RetroTaskQueue;
import async.event.RecolterTaskEvent;
import automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroRessourceCell;
import state.CharacterState;
import state.MapState;

import static automation.PixelConstants.OFFSET_INTERACTION_X;
import static automation.PixelConstants.OFFSET_INTERACTION_Y;

@Slf4j
public class RecolteService {

    private static final RecolteService instance = new RecolteService();
    private final CharacterState characterState = CharacterState.getInstance();
    private final MapState mapState = MapState.getInstance();
    private final RetroTaskQueue retroTaskQueue = RetroTaskQueue.getInstance();

    synchronized public static RecolteService getInstance() {
        return instance;
    }

    public void recolterRessource(RetroRessourceCell ressourceCell) throws InterruptedException {
        log.info("Recolte de la ressource {}", ressourceCell.id());
        Thread.sleep(500);
        NativeWindowsEvents.clic(ressourceCell.getWindowRelativeX(), ressourceCell.getWindowRelativeY());
        Thread.sleep(500);
        NativeWindowsEvents.clic(ressourceCell.getWindowRelativeX() + OFFSET_INTERACTION_X, ressourceCell.getWindowRelativeY() + OFFSET_INTERACTION_Y);
        Thread.sleep(1000);
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
