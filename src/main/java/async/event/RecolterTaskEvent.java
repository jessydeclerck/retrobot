package async.event;

import async.RetroTaskQueue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import model.dofus.RetroRessourceCell;
import service.RecolteService;
import state.CharacterState;
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
    private final RecolteService recolteService = RecolteService.getInstance();

    private final RetroRessourceCell ressourceCell;

    private int processCount = 0; //number of time we tried to process this event

    public RecolterTaskEvent(RetroRessourceCell ressourceCell) {
        this.ressourceCell = ressourceCell;
    }

    public void execute() {
        processCount++;
        if (!isStateOk()) {
            RetroTaskQueue.getInstance().addTask(this);
            return;
        }
        executeTask();
        waitTaskFinished();
        resetState();
    }

    private boolean isStateOk() {
        if (characterState.isFighting() || characterState.isGathering() || characterState.isMoving()) {
            RetroTaskQueue.getInstance().addTask(this);
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
                log.info("La recolte n'a pas abouti, l'événement a été replacé dans la queue");
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
