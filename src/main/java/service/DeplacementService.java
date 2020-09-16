package service;

import automation.NativeWindowsEvents;
import model.dofus.RetroTriggerCell;
import script.ScriptLoader;
import script.model.MapAction;
import script.model.ScriptPath;
import state.CharacterState;
import state.MapState;

import java.util.HashMap;
import java.util.Map;

public class DeplacementService {

    private final ScriptPath scriptPath = ScriptLoader.getInstance().loadScript();

    private final MapState mapState = MapState.getInstance();

    private final CharacterState characterState = CharacterState.getInstance();

    private final MapService mapService = MapService.getInstance();

    private static final DeplacementService instance = new DeplacementService();

    private Map<String, MapAction> gatherMapActions = new HashMap<>();

    private Map<String, MapAction> bankMapActions = new HashMap<>();

    synchronized public static DeplacementService getInstance() {
        return instance;
    }

    public void startDeplacement() {
        scriptPath.getGatherPath().forEach(gp -> gatherMapActions.put(gp.getPos(), gp));
        scriptPath.getBankPath().forEach(bp -> bankMapActions.put(bp.getPos(), bp));
        MapAction startMap = scriptPath.getStartMap();
        mapState.setCurrentMap(mapService.getRetroDofusMap(startMap.getMapId()));
        goNextMap(startMap);
    }

    private void goNextMap(MapAction mapAction) {
        int nextMapId = mapAction.getNextMapId();
        RetroTriggerCell triggerCell = mapState.getCurrentMap().getTriggers().stream().filter(t -> t.getNextMapId() == nextMapId).findAny().get();
        NativeWindowsEvents.clic(triggerCell.getWindowRelativeX(), triggerCell.getWindowRelativeY());
    }

    public void goNextMap() {
        MapAction mapActionToExecute = gatherMapActions.get(getPos());
        goNextMap(mapActionToExecute);
    }

    public void goToBank() {
        characterState.setGoingBank(true);
        MapAction mapActionToExecute = bankMapActions.get(getPos());
        goNextMap(mapActionToExecute);
    }

    private String getPos() {
        return mapState.getCurrentMap().getX() + "," + mapState.getCurrentMap().getY();
    }


}
