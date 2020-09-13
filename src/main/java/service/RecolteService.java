package service;

import async.RetroTaskQueue;
import async.event.RecolterTaskEvent;
import automation.NativeWindowsEvents;
import lombok.extern.log4j.Log4j2;
import model.dofus.RetroRessourceCell;
import state.CharacterState;
import state.MapState;

import static automation.PixelConstants.OFFSET_INTERACTION_X;
import static automation.PixelConstants.OFFSET_INTERACTION_Y;

@Log4j2
public class RecolteService {

    private static final RecolteService instance = new RecolteService();

    synchronized public static RecolteService getInstance() {
        return instance;
    }

    public void recolterRessource(RetroRessourceCell ressourceCell) throws InterruptedException {
        //TODO une ressource peut se trouver trop proche et être fauchée à la place
        log.info("Recolte de la ressource {}", ressourceCell.id());
        Thread.sleep(500);
        NativeWindowsEvents.clic(ressourceCell.getWindowRelativeX(), ressourceCell.getWindowRelativeY());
        Thread.sleep(500);
        NativeWindowsEvents.clic(ressourceCell.getWindowRelativeX() + OFFSET_INTERACTION_X, ressourceCell.getWindowRelativeY() + OFFSET_INTERACTION_Y);
        Thread.sleep(1000);
        RetroRessourceCell targetedRessourceCell = CharacterState.getInstance().getCurrentGatheringTarget();
        if (targetedRessourceCell != null && !ressourceCell.equals(targetedRessourceCell)) {
            log.info("La ressource ciblée est différente de celle prévue : {} vs {}", ressourceCell.id(), targetedRessourceCell.id());
            if (MapState.getInstance().getAvailableRessources().containsKey(ressourceCell.id())) {
                RetroTaskQueue.getInstance().addTask(new RecolterTaskEvent(ressourceCell));
            }
        }
        CharacterState.getInstance().setCurrentGatheringTarget(null);
    }
}
