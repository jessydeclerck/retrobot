package com.retrobot.bot.service;

import com.retrobot.bot.model.dofus.RetroTriggerCell;
import com.retrobot.bot.state.CharacterState;
import com.retrobot.bot.state.MapState;
import com.retrobot.scriptloader.model.BankMapAction;
import com.retrobot.scriptloader.model.GatherMapAction;
import com.retrobot.scriptloader.model.MapAction;
import com.retrobot.scriptloader.model.ScriptPath;
import com.retrobot.utils.automation.NativeWindowsEvents;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class DeplacementService {

    //TODO use dependency injection
    private final ScriptPath scriptPath;

    private final CharacterState characterState;

    private final MapService mapService;

    private final MapState mapState;

    private final TaskService taskService;

    private Map<Integer, GatherMapAction> gatherMapActions;

    private Map<Integer, BankMapAction> bankMapActions;

    public int bankMapId;

    public DeplacementService(ScriptPath scriptPath, CharacterState characterState, MapService mapService, MapState mapState, TaskService taskService) {
        this.scriptPath = scriptPath;
        this.characterState = characterState;
        this.mapService = mapService;
        this.mapState = mapState;
        this.taskService = taskService;
    }

    @PostConstruct
    public void init() {
        startDeplacement();
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

    public void goToGather() {
        characterState.setGoingBank(false);
        MapAction mapActionToExecute = gatherMapActions.get(mapState.getCurrentMap().getId());
        goNextGatherMap(mapActionToExecute);
    }

    private String getPos() {
        return mapState.getCurrentMap().getX() + "," + mapState.getCurrentMap().getY();
    }

    public void startRecolte(GatherMapAction nextMapAction) {
        characterState.setGathering(false); //interrompt l'exécution de la recolte en cours
        if (mapState.getAvailableRessources().isEmpty()) {
            goNextGatherMap(nextMapAction);
        } else {
            mapState.getAvailableRessources().forEach((integer, ressourceCell) -> taskService.queueTaskRecolte(ressourceCell));
        }
    }

    public void stopRecolte() {
        mapState.resetMapState();
        taskService.removeMapTask(mapState.getCurrentMap());
    }

}
