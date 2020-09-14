package async.event;

import async.RetroTaskQueue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import model.dofus.RetroRessourceCell;
import service.RecolteService;
import state.CharacterState;
import state.MapState;
import utils.TimeUtils;

@Log4j2
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
    private final RecolteService recolteService = RecolteService.getInstance();

    private final RetroRessourceCell ressourceCell;

    private int processCount = 0; //number of time we tried to process this event

    public RecolterTaskEvent(RetroRessourceCell ressourceCell) {
        this.ressourceCell = ressourceCell;
    }

    public void execute() {
        if(!isCoherent()){
            log.debug("Incoherent task, discarding");
            return;
        }
        processCount++;
        if (!isStateOk()) {
            RetroTaskQueue.getInstance().addTask(this);
            return;
        }
        executeTask();
        waitTaskFinished();
        resetState();
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
        if (ressourceCell.equals(characterState.getCurrentCellTarget())) {
            log.debug("La prochaine ressource est trop proche du personnage");
            return false;
        }
        return true;
    }

    private void executeTask() {
        try {
            Thread.sleep(200);
            recolteService.recolterRessource(this.ressourceCell);
        } catch (InterruptedException e) {
            log.error(e);
        }
        characterState.setGathering(true);
    }

    private void waitTaskFinished() {
        long startTime = System.currentTimeMillis();
        while (characterState.isGathering()) {
            if ((System.currentTimeMillis() - startTime) > 20000) {
                log.debug("La recolte n'a pas abouti, l'événement a été replacé dans la queue");
                RetroTaskQueue.getInstance().addTask(this);
                break;
            }
            TimeUtils.sleep(200);
        }
    }

    private void resetState() {
        characterState.setGathering(false);
    }

}
