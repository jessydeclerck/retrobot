package service;

import automation.NativeWindowsEvents;
import model.dofus.RetroTriggerCell;
import script.ScriptLoader;
import script.model.BankMapAction;
import script.model.GatherMapAction;
import script.model.MapAction;
import script.model.ScriptPath;
import state.CharacterState;
import state.MapState;

import java.util.Map;

public class DeplacementService {

    //TODO use dependency injection
    private final ScriptPath scriptPath = ScriptLoader.getInstance().loadScript();

    private final CharacterState characterState = CharacterState.getInstance();

    private final MapService mapService = MapService.getInstance();

    private Map<Integer, GatherMapAction> gatherMapActions;

    private Map<Integer, BankMapAction> bankMapActions;

    public int bankMapId;

    private static final DeplacementService instance = new DeplacementService();

    synchronized public static DeplacementService getInstance() {
        return instance;
    }

    public void startDeplacement() {
        this.gatherMapActions = scriptPath.getGatherPath();
        this.bankMapActions = scriptPath.getBankPath();
        int startMapId = scriptPath.getStartMapId();
        this.bankMapId = scriptPath.getBankMapId();
        MapAction startMap = gatherMapActions.get(startMapId);
        MapState.getInstance().setCurrentMap(mapService.getRetroDofusMap(startMapId));
        goNextGatherMap(startMap);
    }

    public void goNextGatherMap(MapAction mapAction) {
        characterState.setMoving(true);
        int nextMapId = mapAction.getNextMapId();
        RetroTriggerCell triggerCell = MapState.getInstance().getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == nextMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void goNextGatherMap() {
        GatherMapAction mapActionToExecute = gatherMapActions.get(MapState.getInstance().getCurrentMap().getId());
        if (mapActionToExecute.isGather() && !characterState.isGoingBank()) {
            MapState.getInstance().startRecolte(mapActionToExecute);
        } else {
            goNextGatherMap(mapActionToExecute);
        }
    }

    public void goToBank() {
        MapState.getInstance().stopRecolte();
        characterState.setGoingBank(true);
        BankMapAction mapActionToExecute = bankMapActions.get(MapState.getInstance().getCurrentMap().getId());
        if (MapState.getInstance().getCurrentMap().getId() == bankMapId && characterState.isGoingBank()) {
            enterBank();
        } else {
            goNextGatherMap(mapActionToExecute);
        }
    }

    public void leaveBank() {
        characterState.setGoingBank(false);
        BankMapAction mapActionToExecute = bankMapActions.get(MapState.getInstance().getCurrentMap().getId());
        goNextGatherMap(mapActionToExecute);
    }

    public void enterBank() {
        characterState.setMoving(true);
        RetroTriggerCell triggerCell = MapState.getInstance().getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == bankMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void goToGather() {
        characterState.setGoingBank(false);
        MapAction mapActionToExecute = gatherMapActions.get(MapState.getInstance().getCurrentMap().getId());
        goNextGatherMap(mapActionToExecute);
    }

    private String getPos() {
        return MapState.getInstance().getCurrentMap().getX() + "," + MapState.getInstance().getCurrentMap().getY();
    }


}
