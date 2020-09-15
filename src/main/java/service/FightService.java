package service;

import automation.NativeWindowsEvents;
import fr.arakne.utils.maps.path.Decoder;
import fr.arakne.utils.maps.path.Path;
import fr.arakne.utils.maps.path.Pathfinder;
import lombok.extern.slf4j.Slf4j;
import model.dofus.RetroDofusCell;
import state.MapState;

@Slf4j
public class FightService {

    private Decoder<RetroDofusCell> decoder;

    //TODO WIP
    private Path<RetroDofusCell> calculatePath(RetroDofusCell currentCell, RetroDofusCell targetCell) {
        decoder = new Decoder<>(MapState.getInstance().getCurrentMap());
        Pathfinder<RetroDofusCell> pathfinder = decoder.pathfinder() //portee sort
                .targetDistance(1)
                .walkablePredicate(r -> r.walkable());
        Path<RetroDofusCell> path = pathfinder.findPath(currentCell, targetCell).truncate(4);// number of pm


        return path;
    }

    public void moveTowardMonster(RetroDofusCell playerCell, RetroDofusCell monsterCell) {
        log.info("Calculating path from player cell {} to monster cell {}", playerCell.id(), monsterCell.id());
        Path<RetroDofusCell> path = calculatePath(playerCell, monsterCell);
        RetroDofusCell targetCell = path.target();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        log.info("Player movement towards cell {}", targetCell.id());
        log.info("Cell pos : {}, {}", targetCell.getAbscisse(), targetCell.getOrdonnee());
        NativeWindowsEvents.clic(targetCell.getWindowRelativeX(), targetCell.getWindowRelativeY()); //TODO refacto externalize
    }

}
