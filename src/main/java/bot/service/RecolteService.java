package bot.service;

import bot.async.RetroTaskQueue;
import bot.async.event.RecolterTaskEvent;
import utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import bot.model.dofus.RetroRessourceCell;
import bot.state.CharacterState;
import bot.state.MapState;
import utils.TimeUtils;

import static utils.automation.PixelConstants.OFFSET_INTERACTION_X;
import static utils.automation.PixelConstants.OFFSET_INTERACTION_Y;

@Slf4j
public class RecolteService {

    private static final RecolteService instance = new RecolteService();
    private final CharacterState characterState = CharacterState.getInstance();
    private final MapState mapState = MapState.getInstance();
    private final RetroTaskQueue retroTaskQueue = RetroTaskQueue.getInstance();
    private final static int OFFSET_MILIEU_CASE_X = 20, OFFSET_MILIEU_CASE_Y = 30;

    synchronized public static RecolteService getInstance() {
        return instance;
    }

    //TODO only works for cereals
    public void recolterRessource(RetroRessourceCell ressourceCell) {
        log.info("Recolte de la ressource {}", ressourceCell.id());
        //TODO handle right extrremity ressources
        TimeUtils.sleep(200);
        double x = ressourceCell.getWindowRelativeX() + OFFSET_MILIEU_CASE_X, y = ressourceCell.getWindowRelativeY() - OFFSET_MILIEU_CASE_Y;
        NativeWindowsEvents.clic(x, y);
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

    public void fermerPopupNiveauMetier() {
        TimeUtils.sleep(1000);
        NativeWindowsEvents.clic(473, 328);

    }
}
