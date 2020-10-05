package com.retrobot.bot.async;

import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.constants.GatheringConstants;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.bot.service.BotService;
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
    private final BotService botService;

    public RetroTaskEventExecutor(CharacterState characterState, MapState mapState, RecolteService recolteService, RetroTaskQueue retroTaskQueue, BotService botService) {
        this.characterState = characterState;
        this.mapState = mapState;
        this.recolteService = recolteService;
        this.retroTaskQueue = retroTaskQueue;
        this.botService = botService;
    }

    public void execute(RecolterTaskEvent retroTaskEvent) {
        if (!isCoherent(retroTaskEvent.getRessourceCell())) {
            log.info("Incoherent task, discarding");
            botService.setUnavailableRessource(retroTaskEvent.getRessourceCell().id());
            return;
        }
        if (isRessourceToRightExtremity(retroTaskEvent.getRessourceCell())) {  //ignore right extremity of the map
            log.info("On ignore la ressource car elle est à l'extrémité droite de la carte");
            botService.setUnavailableRessource(retroTaskEvent.getRessourceCell().id());
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
        mapState.getMonsterPositions().values().forEach(monsterPosition -> log.info("Groupe de monstre sur la cellule : {}", monsterPosition));
        if (mapState.getMonsterPositions().values().contains(ressourceCell.id())) {
            log.info("Un groupe de monstre est sur la case de la ressource");
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
            TimeUtils.sleep(500);
            if ((System.currentTimeMillis() - startTime) > GatheringConstants.GATHERING_TIMEOUT && !characterState.isGatheringConfirmed() && mapState.getAvailableRessources().containsKey(recolterTaskEvent.getRessourceCell().id())) {
                log.info("La recolte n'a pas abouti, l'événement a été replacé dans la queue");
                retroTaskQueue.addTask(recolterTaskEvent);
                break;
            }
        }
    }

    private void resetState() {
        characterState.setGathering(false);
    }
}
