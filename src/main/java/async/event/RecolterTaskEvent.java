package async.event;

import lombok.Getter;
import service.RecolteService;
import state.CharacterState;
import utils.TimeUtils;

@Getter
public class RecolterTaskEvent {

    private RetroBotTask retroBotTask = RetroBotTask.RECOLTER;

    private final CharacterState characterState = CharacterState.getInstance();
    private final RecolteService recolteService = RecolteService.getInstance();

    private final int windowRelativeX;

    private final int windowRelativeY;


    public RecolterTaskEvent(int windowRelativeX, int windowRelativeY) {
        this.windowRelativeX = windowRelativeX;
        this.windowRelativeY = windowRelativeY;
    }

    public void execute() {
        executeTask();
        waitTaskFinished();
        resetState();
    }

    private void executeTask() {
        recolteService.recolterRessource(this.getWindowRelativeX(), this.getWindowRelativeY());
        characterState.setGathering(true);
    }

    private void waitTaskFinished() {
        long startTime = System.currentTimeMillis();
        while (characterState.isGathering()) {
            if ((System.currentTimeMillis() - startTime) > 20000) break;
            TimeUtils.sleep(200);
        }
    }

    private void resetState() {
        characterState.setGathering(false);
    }

}
