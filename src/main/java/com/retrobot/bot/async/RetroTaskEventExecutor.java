package com.retrobot.bot.async;

import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.bot.service.RecolteService;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetroTaskEventExecutor {

    private final CharacterState characterState;
    private final MapState mapState;
    private final RecolteService recolteService;
    private final RetroTaskQueue retroTaskQueue;

    public RetroTaskEventExecutor(CharacterState characterState, MapState mapState, RecolteService recolteService, RetroTaskQueue retroTaskQueue) {
        this.characterState = characterState;
        this.mapState = mapState;
        this.recolteService = recolteService;
        this.retroTaskQueue = retroTaskQueue;
    }

    public void execute(RecolterTaskEvent retroTaskEvent) {
        if (!isCoherent(retroTaskEvent.getRessourceCell())) {
            log.info("Incoherent task, discarding");
            return;
        }
        if (isRessourceToRightExtremity(retroTaskEvent.getRessourceCell())) {  //ignore right extremity of the map
            log.info("On ignore la ressource car elle est à l'extrémité droite de la carte");
            return;
        }
        retroTaskEvent.incrementProcessCount();
        if (!isStateOk(retroTaskEvent.getRessourceCell())) {
            retroTaskQueue.addTask(retroTaskEvent);
            return;
        }
        executeTask(retroTaskEvent.getRessourceCell());
        waitTaskFinished(retroTaskEvent);
        resetState();
        log.info("Recolte terminée");
    }

    private boolean isRessourceToRightExtremity(RetroRessourceCell ressourceCell) {
        return ressourceCell.getAbscisse() >= mapState.getCurrentMap().getWidth() * 2 - 3;
    }

    private boolean isCoherent(RetroRessourceCell ressourceCell) {
        if (mapState.getCurrentMap().equals(ressourceCell.map())) {
            return true;
        }
        //TODO find a way to check if ressource still available
        return false;
    }

    private boolean isStateOk(RetroRessourceCell ressourceCell) {
        if (characterState.isFighting() || characterState.isGathering() || characterState.isMoving()) {
            return false;
        }
        if (characterState.getCurrentPlayerCell() != null && ressourceCell.id() == characterState.getCurrentPlayerCell().id()) {
            log.info("La prochaine ressource est trop proche du personnage");
            return false;
        }
        return true;
    }

    private void executeTask(RetroRessourceCell ressourceCell) {
        TimeUtils.sleep(200);
        recolteService.recolterRessource(ressourceCell);
        characterState.setGathering(true);
    }

    private void waitTaskFinished(RecolterTaskEvent recolterTaskEvent) {
        long startTime = System.currentTimeMillis();
        while (characterState.isGathering()) {
            if ((System.currentTimeMillis() - startTime) > 20000) {
                log.info("La recolte n'a pas abouti, l'événement a été replacé dans la queue");
                retroTaskQueue.addTask(recolterTaskEvent);
                break;
            }
            TimeUtils.sleep(200);
        }
    }

    private void resetState() {
        characterState.setGathering(false);
    }
}
