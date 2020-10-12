package com.retrobot.bot.state;

import com.retrobot.bot.constants.GatheringConstants;
import com.retrobot.bot.model.dofus.RetroDofusCell;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Service
public class CharacterState {

    private boolean gathering;
    private boolean gatheringConfirmed;
    private boolean fighting;
    private boolean moving;
    private boolean goingBank;
    private boolean paysan;
    private int playerId;
    private RetroRessourceCell currentGatheringTarget;
    private RetroDofusCell currentCellTarget;
    private RetroDofusCell currentPlayerCell;
    private int currentPods;
    private int maxPods;
    private int currentPa;
    private int currentPm;

    private final TaskExecutor taskExecutor;

    //TODO refactor
    private RetroDofusCell currentFightCell;

    private Map<Integer, RetroDofusCell> currentFightMonsterCells = new HashMap<>();
    private int fightMonstersNumber;

    public CharacterState(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setGathering(boolean gathering) {
        this.gathering = gathering;
        if (gathering) {
            taskExecutor.execute(() -> {
                Integer gatheringTargetId = currentGatheringTarget != null ? currentGatheringTarget.id() : null;
                TimeUtils.sleep(GatheringConstants.GATHERING_TIMEOUT);
                if (!this.gatheringConfirmed && (gatheringTargetId == null || gatheringTargetId == currentGatheringTarget.id())) { // need to recheck as target could have change when gathering time < timeout
                    this.setGathering(false);
                }
            });
        } else {
            this.gatheringConfirmed = false;
        }
    }

}
