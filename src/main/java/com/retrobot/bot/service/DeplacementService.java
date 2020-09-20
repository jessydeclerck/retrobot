package com.retrobot.bot.service;

import com.retrobot.bot.async.RetroTaskQueue;
import com.retrobot.bot.async.event.RecolterTaskEvent;
import com.retrobot.bot.model.dofus.RetroTriggerCell;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.scriptloader.model.BankMapAction;
import com.retrobot.scriptloader.model.GatherMapAction;
import com.retrobot.scriptloader.model.MapAction;
import com.retrobot.scriptloader.model.ScriptPath;
import com.retrobot.utils.TimeUtils;
import com.retrobot.utils.automation.NativeWindowsEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class DeplacementService {

    private final ScriptPath scriptPath;

    private final CharacterState characterState;

    private final MapService mapService;

    private final MapState mapState;

    private final RetroTaskQueue retroTaskQueue;

    private Map<Integer, GatherMapAction> gatherMapActions;

    private Map<Integer, BankMapAction> bankMapActions;

    public int bankMapId;

    public DeplacementService(ScriptPath scriptPath, CharacterState characterState, MapService mapService, MapState mapState, RetroTaskQueue retroTaskQueue) {
        this.scriptPath = scriptPath;
        this.characterState = characterState;
        this.mapService = mapService;
        this.mapState = mapState;
        this.retroTaskQueue = retroTaskQueue;
    }

    public void startDeplacement() {
        this.gatherMapActions = scriptPath.getGatherPath();
        this.bankMapActions = scriptPath.getBankPath();
        int startMapId = scriptPath.getStartMapId();
        this.bankMapId = scriptPath.getBankMapId();
        MapAction startMap = gatherMapActions.get(startMapId);
        mapState.setCurrentMap(mapService.getRetroDofusMap(startMapId));
        goNextGatherMap(startMap);
    }

    public void goNextMap() {
        CompletableFuture.runAsync(() -> {
            TimeUtils.sleep(2000);
            if (!characterState.isGoingBank()) {
                changeMapWithRetry(this::goNextGatherMap, mapState.getCurrentMap().getId()); //wont be compatible with gathering
            } else {
                changeMapWithRetry(this::goToBank, mapState.getCurrentMap().getId());
            }
        });
    }

    private void changeMapWithRetry(Runnable changeMapAction, int startMapId) {
        changeMapAction.run();
        CompletableFuture.runAsync(() -> {
            TimeUtils.sleep(15000);
            int currentMapId = mapState.getCurrentMap().getId();
            Optional<GatherMapAction> gatherMapAction = Optional.ofNullable(scriptPath.getGatherPath().get(currentMapId));
            if (startMapId == currentMapId) {
                if (gatherMapAction.isPresent() && gatherMapAction.get().isGather()) {
                    log.info("Map didn't change because we're on a gathering map, won't be retried");
                } else {
                    log.info("Map didn't change, let's retry");
                    changeMapWithRetry(changeMapAction, startMapId);
                }
            } else {
                log.info("Map has changed, won't be retried");
            }
        });
    }

    public void goNextGatherMap(MapAction mapAction) {
        characterState.setMoving(true);
        int nextMapId = mapAction.getNextMapId();
        RetroTriggerCell triggerCell = mapState.getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == nextMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void goNextGatherMap() {
        GatherMapAction mapActionToExecute = gatherMapActions.get(mapState.getCurrentMap().getId());
        if (mapActionToExecute.isGather() && !characterState.isGoingBank()) {
            startRecolte(mapActionToExecute);
        } else {
            goNextGatherMap(mapActionToExecute);
        }
    }

    public void goToBank() {
        stopRecolte();
        characterState.setGoingBank(true);
        BankMapAction mapActionToExecute = bankMapActions.get(mapState.getCurrentMap().getId());
        if (mapState.getCurrentMap().getId() == bankMapId && characterState.isGoingBank()) {
            enterBank();
        } else {
            goNextGatherMap(mapActionToExecute);
        }
    }

    public void leaveBank() {
        characterState.setGoingBank(false);
        BankMapAction mapActionToExecute = bankMapActions.get(mapState.getCurrentMap().getId());
        goNextGatherMap(mapActionToExecute);
    }

    public void enterBank() {
        characterState.setMoving(true);
        RetroTriggerCell triggerCell = mapState.getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == bankMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void startRecolte(GatherMapAction nextMapAction) {
        characterState.setGathering(false); //interrompt l'exécution de la recolte en cours
        if (mapState.getAvailableRessources().isEmpty()) {
            goNextGatherMap(nextMapAction);
        } else {
            mapState.getAvailableRessources().forEach((integer, ressourceCell) -> retroTaskQueue.addTask(new RecolterTaskEvent(ressourceCell, DeplacementService.class)));
        }
    }

    public void stopRecolte() {
        mapState.resetMapState();
    }

}
