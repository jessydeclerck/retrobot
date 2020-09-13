package state;

import lombok.Data;
import model.dofus.RetroDofusMap;
import model.dofus.RetroRessourceCell;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class MapState {

    private static final MapState instance = new MapState();
    private RetroDofusMap currentMap;
    private Map<Integer, RetroRessourceCell> availableRessources = new ConcurrentHashMap<>();
    private Map<Integer, RetroRessourceCell> unavailableRessources = new ConcurrentHashMap<>();

    synchronized public static final MapState getInstance() {
        return instance;
    }

}
