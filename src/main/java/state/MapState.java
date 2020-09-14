package state;

import async.RetroTaskQueue;
import async.event.RecolterTaskEvent;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import model.dofus.RetroDofusMap;
import model.dofus.RetroRessourceCell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Log4j2
public class MapState {

    private static final MapState instance = new MapState();
    private RetroDofusMap currentMap;
    private Map<Integer, RetroRessourceCell> availableRessources = new ConcurrentHashMap<>();
    private Map<Integer, RetroRessourceCell> unavailableRessources = new ConcurrentHashMap<>();

    synchronized public static final MapState getInstance() {
        return instance;
    }

    public void addAvailableRessources(List<RetroRessourceCell> ressourcesCells) {
        ressourcesCells.forEach(ressourceCell -> {
            if (availableRessources.containsKey(ressourceCell.id())) {
                return;
            }
            availableRessources.put(ressourceCell.id(), ressourceCell);
            //log.info("Ressource disponible : {}, {}", ressourceCell.getWindowRelativeX(), ressourceCell.getWindowRelativeY());
        });
    }

    public void startRecolte() {
        availableRessources.forEach((integer, ressourceCell) -> {
            RetroTaskQueue.getInstance().addTask(new RecolterTaskEvent(ressourceCell));
        });
    }

    public void setUnavailableRessource(int cellId) {
        RetroRessourceCell unavailableRessourceCell = availableRessources.get(cellId);
        if (unavailableRessourceCell == null) return;
        availableRessources.remove(cellId);
        unavailableRessources.put(cellId, unavailableRessourceCell);
        log.info("Ressource indisponible : {}", unavailableRessourceCell.id());
        logAvailableRessources();
        RetroTaskQueue.getInstance().removeTask(new RecolterTaskEvent(unavailableRessourceCell));
    }

    public void setAvailableRessource(int cellId) {
        RetroRessourceCell availableRessourceCell = unavailableRessources.get(cellId);
        if (availableRessourceCell == null) return;
        unavailableRessources.remove(cellId);
        unavailableRessources.put(cellId, availableRessourceCell);
        //log.info("Ressource disponible : {}, {}", availableRessourceCell.getWindowRelativeX(), availableRessourceCell.getWindowRelativeY());
        RetroTaskQueue.getInstance().addTask(new RecolterTaskEvent(availableRessourceCell));
    }

    private void logAvailableRessources() {
        this.availableRessources.keySet().stream().forEach(key -> {
            RetroRessourceCell ressourceCell = availableRessources.get(key);
            log.info("Ressource disponible : {}", ressourceCell.id());
        });
    }

    public void setCurrentMap(RetroDofusMap newMap) {
        if(currentMap == null) {
            this.currentMap = newMap;
            return;
        }
        CharacterState characterState = CharacterState.getInstance();
        if (currentMap != newMap) {
            currentMap.getTriggers().stream().filter(t -> t.id() == characterState.getCurrentCellTarget().id())
                    .findFirst().ifPresent(t -> characterState.setCurrentCellTarget(newMap.get(t.getNextCellId())));
            log.info("New map character cell id : {}", characterState.getCurrentCellTarget().id());
        }
        this.resetMapState();
        this.currentMap = newMap;
    }

    public void resetMapState() {
        this.availableRessources = new HashMap<>();
        this.unavailableRessources = new HashMap<>();
        RetroTaskQueue.getInstance().removeMapTask(this.currentMap);
    }

}
