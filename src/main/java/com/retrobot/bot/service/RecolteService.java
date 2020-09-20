package com.retrobot.bot.service;

import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.utils.TimeUtils;
import com.retrobot.utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.retrobot.utils.automation.PixelConstants.OFFSET_INTERACTION_X;
import static com.retrobot.utils.automation.PixelConstants.OFFSET_INTERACTION_Y;

@Slf4j
@Service
public class RecolteService {

    private final CharacterState characterState;
    private final MapState mapState;
    private final RetroTaskQueue retroTaskQueue;
    private final static int OFFSET_MILIEU_CASE_X = 20, OFFSET_MILIEU_CASE_Y = 30;

    public RecolteService(CharacterState characterState, MapState mapState, RetroTaskQueue retroTaskQueue) {
        this.characterState = characterState;
        this.mapState = mapState;
        this.retroTaskQueue = retroTaskQueue;
    }

    //TODO only works for cereals
    public void recolterRessource(RetroRessourceCell ressourceCell) {
        log.info("Recolte de la ressource {}", ressourceCell.id());
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
                retroTaskQueue.addTask(new RecolterTaskEvent(ressourceCell, RecolteService.class));
            }
        }
        characterState.setCurrentGatheringTarget(null);
    }

    public void fermerPopupNiveauMetier() {
        TimeUtils.sleep(1000);
        NativeWindowsEvents.clic(473, 328);
    }


}
