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

    private final ScriptPath scriptPath = ScriptLoader.getInstance().loadScript();

    private final MapState mapState = MapState.getInstance();

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
        mapState.setCurrentMap(mapService.getRetroDofusMap(startMapId));
        goNextMap(startMap);
    }

    private void goNextMap(MapAction mapAction) {
        int nextMapId = mapAction.getNextMapId();
        RetroTriggerCell triggerCell = mapState.getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == nextMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void goNextMap() {
        MapAction mapActionToExecute = gatherMapActions.get(mapState.getCurrentMap().getId());
        goNextMap(mapActionToExecute);
    }

    public void goToBank() {
        characterState.setGoingBank(true);
        BankMapAction mapActionToExecute = bankMapActions.get(mapState.getCurrentMap().getId());
        if (mapState.getCurrentMap().getId() == bankMapId && characterState.isGoingBank()) {
            enterBank();
        } else {
            goNextMap(mapActionToExecute);
        }
    }

    public void leaveBank() {
        characterState.setGoingBank(false);
        BankMapAction mapActionToExecute = bankMapActions.get(mapState.getCurrentMap().getId());
        goNextMap(mapActionToExecute);
    }

    public void enterBank() {
        RetroTriggerCell triggerCell = mapState.getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == bankMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void goToGather() {
        characterState.setGoingBank(false);
        MapAction mapActionToExecute = gatherMapActions.get(mapState.getCurrentMap().getId());
        goNextMap(mapActionToExecute);
    }

    private String getPos() {
        return mapState.getCurrentMap().getX() + "," + mapState.getCurrentMap().getY();
    }


}
