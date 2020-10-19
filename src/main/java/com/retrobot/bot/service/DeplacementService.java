package com.retrobot.bot.service;

import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.model.dofus.RetroDofusCell;
import com.retrobot.bot.model.dofus.RetroTriggerCell;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.scriptloader.model.gathering.BankMapAction;
import com.retrobot.scriptloader.model.gathering.GatherMapAction;
import com.retrobot.scriptloader.model.gathering.MapAction;
import com.retrobot.scriptloader.model.gathering.ScriptPath;
import com.retrobot.utils.TimeUtils;
import com.retrobot.utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class DeplacementService {

    private final ScriptPath scriptPath;

    private final CharacterState characterState;

    private final MapService mapService;

    private final MapState mapState;

    private final RetroTaskQueue retroTaskQueue;

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private Map<Integer, GatherMapAction> gatherMapActions;

    private Map<Integer, BankMapAction> bankMapActions;

    public int bankMapId;

    public DeplacementService(ScriptPath scriptPath, CharacterState characterState, MapService mapService, MapState mapState, RetroTaskQueue retroTaskQueue, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.scriptPath = scriptPath;
        this.characterState = characterState;
        this.mapService = mapService;
        this.mapState = mapState;
        this.retroTaskQueue = retroTaskQueue;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public void startDeplacement() {
        this.gatherMapActions = scriptPath.getGatherPath();
        this.bankMapActions = scriptPath.getBankPath();
        int startMapId = scriptPath.getStartMapId();
        this.bankMapId = scriptPath.getBankMapId();
        MapAction startMap = gatherMapActions.get(startMapId);
        mapState.setCurrentMap(mapService.getRetroDofusMap(startMapId));
        executeNextMapAction(startMap);
    }

    public void goNextMap() {
        threadPoolTaskExecutor.execute(() -> {
            TimeUtils.sleep(1000);
            if (!characterState.isGoingBank()) {
                changeMapWithRetry(this::goNextGatherMap, mapState.getCurrentMap().getId()); //wont be compatible with gathering
            } else {
                changeMapWithRetry(this::goToBank, mapState.getCurrentMap().getId());
            }
        });
    }

    private void changeMapWithRetry(Runnable changeMapAction, int startMapId) {
        changeMapAction.run();
        threadPoolTaskExecutor.execute(() -> {
            TimeUtils.sleep(15000);
            int currentMapId = mapState.getCurrentMap().getId();
            Optional<GatherMapAction> gatherMapAction = Optional.ofNullable(scriptPath.getGatherPath().get(currentMapId));
            if (startMapId == currentMapId) {
                if (gatherMapAction.isPresent() && gatherMapAction.get().isGather() && !characterState.isGoingBank()) {
                    log.info("Map didn't change because we're on a gathering map, won't be retried");
                } else if (characterState.isGoingBank() && mapState.getCurrentMap().getId() == scriptPath.getBankMapId()) {
                    log.info("Map didn't change because we're at the bank");
                } else if (characterState.isFighting()) {
                    log.info("Map didn't change because we're fighting");
                } else {
                    log.info("Map didn't change, let's retry");
                    changeMapWithRetry(changeMapAction, startMapId);
                }
            } else {
                log.info("Map has changed, won't be retried");
            }
        });
    }

    public void executeNextMapAction(MapAction mapAction) {
        log.info("Going next map");
        characterState.setMoving(true);
        int nextMapId = mapAction.getNextMapId();
        Optional<RetroTriggerCell> triggerCell = mapState.getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == nextMapId).findAny();
        if (!triggerCell.isPresent()) {
            log.error("Cant find trigger to next map. Current map id : {}. Next map id : {}", mapState.getCurrentMap().getId(), nextMapId);
        }
        triggerCell.ifPresent(cell ->
                NativeWindowsEvents.clic(cell.getWindowRelativeX(), cell.getWindowRelativeY())
        );
    }

    public void goNextGatherMap() {
        GatherMapAction mapActionToExecute = gatherMapActions.get(mapState.getCurrentMap().getId());
        if (mapActionToExecute.isGather() && !characterState.isGoingBank()) {
            startRecolte(mapActionToExecute);
        } else if (mapActionToExecute.isFight() && !mapState.getMonsterPositions().values().isEmpty()) {
            startFight(mapActionToExecute);
        } else {
            executeNextMapAction(mapActionToExecute);
        }
    }

    private void startFight(GatherMapAction mapActionToExecute) {
        mapState.getMonsterPositions().values().stream().filter(monsterPos ->
                !mapState.getCurrentMap().getTriggers().contains(monsterPos) && !mapState.getPlayerPositions().values().contains(monsterPos)
        )
                .findAny().ifPresent(monsterPos -> {
            RetroDofusCell monsterCell = mapState.getCurrentMap().get(monsterPos);
            NativeWindowsEvents.clic(monsterCell.getWindowRelativeX(), monsterCell.getWindowRelativeY());
        });
    }

    public void goToBank() {
        if (mapState.getCurrentMap().getId() == scriptPath.getBankMapId() && characterState.isGoingBank()) {
            log.info("We're in the bank");
            characterState.setGoingBank(false);
            return;
        }
        log.info("Going bank");
        BankMapAction mapActionToExecute = bankMapActions.get(mapState.getCurrentMap().getId());
        executeNextMapAction(mapActionToExecute);
    }

    public void leaveBank() {
        TimeUtils.sleep(2000);
        log.info("Leaving bank");
        BankMapAction mapActionToExecute = bankMapActions.get(mapState.getCurrentMap().getId());
        executeNextMapAction(mapActionToExecute);
    }

    public void enterBank() {
        log.info("Entering bank");
        characterState.setMoving(true);
        RetroTriggerCell triggerCell = mapState.getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == bankMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void startRecolte(GatherMapAction nextMapAction) {
        characterState.setGathering(false); //interrompt l'exécution de la recolte en cours
        if (mapState.getAvailableRessources().isEmpty()) {
            executeNextMapAction(nextMapAction);
        } else {
            mapState.getAvailableRessources().forEach((integer, ressourceCell) -> retroTaskQueue.addTask(new RecolterTaskEvent(ressourceCell, DeplacementService.class)));
        }
    }

    public void stopRecolte() {
        mapState.resetMapState();
    }

}
