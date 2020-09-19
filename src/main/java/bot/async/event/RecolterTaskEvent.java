package bot.async.event;

import bot.async.RetroTaskQueue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import bot.model.dofus.RetroRessourceCell;
import bot.service.DeplacementService;
import bot.service.FightService;
import bot.service.RecolteService;
import bot.state.CharacterState;
import bot.state.MapState;
import utils.TimeUtils;

@Slf4j
@Getter
@EqualsAndHashCode
@ToString
public class RecolterTaskEvent {

    private final RetroBotTask retroBotTask = RetroBotTask.RECOLTER;

    @EqualsAndHashCode.Exclude
    private final CharacterState characterState = CharacterState.getInstance();
    @EqualsAndHashCode.Exclude
    private final MapState mapState = MapState.getInstance();
    @EqualsAndHashCode.Exclude
    private final FightService fightService = FightService.getInstance();
    @EqualsAndHashCode.Exclude
    private final RecolteService recolteService = RecolteService.getInstance();
    @EqualsAndHashCode.Exclude
    private final RetroTaskQueue retroTaskQueue = RetroTaskQueue.getInstance();
    @EqualsAndHashCode.Exclude
    private final DeplacementService deplacementService = DeplacementService.getInstance();

    private final RetroRessourceCell ressourceCell;

    private int processCount = 0; //number of time we tried to process this event

    public RecolterTaskEvent(RetroRessourceCell ressourceCell) {
        this.ressourceCell = ressourceCell;
    }

    public void execute() {
        if (!isCoherent()) {
            log.info("Incoherent task, discarding");
            return;
        }
        if (isRessourceToRightExtremity()) {  //ignore right extremity of the map
            log.info("On ignore la ressource car elle est à l'extrémité droite de la carte");
            return;
        }
        processCount++;
        if (!isStateOk()) {
            retroTaskQueue.addTask(this);
            return;
        }
        executeTask();
        waitTaskFinished();
        resetState();
        log.info("Recolte terminée");
    }

    private boolean isRessourceToRightExtremity() {
        return ressourceCell.getAbscisse() >= mapState.getCurrentMap().getWidth() * 2 - 3;
    }

    private boolean isCoherent() {
        if (mapState.getCurrentMap().equals(ressourceCell.map())) {
            return true;
        }
        //TODO find a way to check if ressource still available
        return false;
    }

    private boolean isStateOk() {
        if (characterState.isFighting() || characterState.isGathering() || characterState.isMoving()) {
            return false;
        }
        if (characterState.getCurrentPlayerCell() != null && ressourceCell.id() == characterState.getCurrentPlayerCell().id()) {
            log.info("La prochaine ressource est trop proche du personnage");
            return false;
        }
        return true;
    }

    private void executeTask() {
        TimeUtils.sleep(200);
        recolteService.recolterRessource(this.ressourceCell);
        characterState.setGathering(true);
    }

    private void waitTaskFinished() {
        long startTime = System.currentTimeMillis();
        while (characterState.isGathering()) {
            if ((System.currentTimeMillis() - startTime) > 20000) {
                log.info("La recolte n'a pas abouti, l'événement a été replacé dans la queue");
                retroTaskQueue.addTask(this);
                break;
            }
            TimeUtils.sleep(200);
        }
    }

    private void resetState() {
        characterState.setGathering(false);
    }

}
