package state;

import async.RetroTaskQueue;
import async.event.RecolterTaskEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusMap;
import model.dofus.RetroRessourceCell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class MapState {

    private static final MapState instance = new MapState();
    private final CharacterState characterState = CharacterState.getInstance();
    private final RetroTaskQueue retroTaskQueue = RetroTaskQueue.getInstance();
    private RetroDofusMap currentMap;
    private Map<Integer, RetroRessourceCell> availableRessources = new ConcurrentHashMap<>();
    private Map<Integer, RetroRessourceCell> unavailableRessources = new ConcurrentHashMap<>();

    synchronized public static MapState getInstance() {
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
        characterState.setGathering(false); //interrompt l'exécution de la recolte en cours
        availableRessources.forEach((integer, ressourceCell) -> retroTaskQueue.addTask(new RecolterTaskEvent(ressourceCell)));
    }

    public void stopRecolte() {
        retroTaskQueue.removeAll();
    }

    public void setUnavailableRessource(int cellId) {
        RetroRessourceCell unavailableRessourceCell = availableRessources.get(cellId);
        if (unavailableRessourceCell == null) return;
        availableRessources.remove(cellId);
        unavailableRessources.put(cellId, unavailableRessourceCell);
        log.debug("Ressource indisponible : {}", unavailableRessourceCell.id());
        logAvailableRessources();
        retroTaskQueue.removeTask(new RecolterTaskEvent(unavailableRessourceCell));
    }

    public void setAvailableRessource(int cellId) {
        RetroRessourceCell availableRessourceCell = unavailableRessources.get(cellId);
        if (availableRessourceCell == null) return;
        unavailableRessources.remove(cellId);
        unavailableRessources.put(cellId, availableRessourceCell);
        //log.info("Ressource disponible : {}, {}", availableRessourceCell.getWindowRelativeX(), availableRessourceCell.getWindowRelativeY());
        retroTaskQueue.addTask(new RecolterTaskEvent(availableRessourceCell));
    }

    private void logAvailableRessources() {
        this.availableRessources.keySet().forEach(key -> {
            RetroRessourceCell ressourceCell = availableRessources.get(key);
            log.debug("Ressource disponible : {}", ressourceCell.id());
        });
    }

    public void setCurrentMap(RetroDofusMap newMap) {
        if (currentMap == null) {
            this.currentMap = newMap;
            return;
        }
        if (currentMap != newMap) {
            currentMap.getTriggers().stream().filter(t -> t.id() == characterState.getCurrentCellTarget().id())
                    .findFirst().ifPresent(t -> {
                characterState.setCurrentPlayerCell(newMap.get(t.getNextCellId()));
                characterState.setCurrentCellTarget(newMap.get(t.getNextCellId()));
            });
//            log.info("New map character cell id : {}", characterState.getCurrentPlayerCell().id());
        }
        this.resetMapState();
        this.currentMap = newMap;
    }

    public void resetMapState() {
        this.availableRessources = new HashMap<>();
        this.unavailableRessources = new HashMap<>();
        retroTaskQueue.removeMapTask(this.currentMap);
    }

}
