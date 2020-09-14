package service;

import fr.arakne.utils.maps.LineOfSight;
import fr.arakne.utils.maps.path.Decoder;
import fr.arakne.utils.maps.path.Path;
import fr.arakne.utils.maps.path.Pathfinder;
import model.dofus.RetroDofusCell;
import state.MapState;

public class FightService {

    private Decoder<RetroDofusCell> decoder;

    //TODO WIP
    public Path<RetroDofusCell> calculatePath(RetroDofusCell currentCell, RetroDofusCell targetCell) {
        decoder = new Decoder<>(MapState.getInstance().getCurrentMap());
        LineOfSight los = new LineOfSight(MapState.getInstance().getCurrentMap());
  //      if (los.between(currentCell, targetCell)){ TODO needs to implement battefield cell interface ?
            //on peut taper avec un spell
    //    }else {

      //  }
        Pathfinder<RetroDofusCell> pathfinder = decoder.pathfinder() //portee sort
                .targetDistance(1)
                .walkablePredicate(r -> r.walkable());
        Path<RetroDofusCell> path = pathfinder.findPath(currentCell, targetCell).truncate(4);// number of pm


        return path;
    }

}
