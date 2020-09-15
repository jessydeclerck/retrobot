package service;

import automation.NativeWindowsEvents;
import fr.arakne.utils.maps.AbstractCellDataAdapter;
import fr.arakne.utils.maps.path.Decoder;
import fr.arakne.utils.maps.path.Path;
import fr.arakne.utils.maps.path.Pathfinder;
import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusCell;
import state.MapState;
import utils.TimeUtils;

@Slf4j
public class FightService {


    private static final FightService instance = new FightService();

    private final MapState mapState = MapState.getInstance();

    synchronized public static FightService getInstance() {
        return instance;
    }


    //TODO WIP
    private Path<RetroDofusCell> calculatePath(RetroDofusCell currentCell, RetroDofusCell targetCell) {
        Decoder<RetroDofusCell> decoder = new Decoder<>(mapState.getCurrentMap());
        Pathfinder<RetroDofusCell> pathfinder = decoder.pathfinder()
                .targetDistance(1)//TODO portee sort
                .walkablePredicate(AbstractCellDataAdapter::walkable);
        return pathfinder.findPath(currentCell, targetCell).truncate(4); //TODO number of pm
    }

    public void moveTowardMonster(RetroDofusCell playerCell, RetroDofusCell monsterCell) {
        log.info("Calculating path from player cell {} to monster cell {}", playerCell.id(), monsterCell.id());
        Path<RetroDofusCell> path = calculatePath(playerCell, monsterCell);
        RetroDofusCell targetCell = path.target();
        TimeUtils.sleep(500);
        log.info("Player movement towards cell {}", targetCell.id());
        log.info("Cell pos : {}, {}", targetCell.getAbscisse(), targetCell.getOrdonnee());
        NativeWindowsEvents.clic(targetCell.getWindowRelativeX(), targetCell.getWindowRelativeY()); //TODO refacto externalize
    }

}
