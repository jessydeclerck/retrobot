package async.event;

import model.dofus.RetroDofusCell;
import model.dofus.RetroRessourceCell;
import state.CharacterState;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class TaskPriorityComparator implements Comparator<RecolterTaskEvent> {
    @Override
    public int compare(RecolterTaskEvent o1, RecolterTaskEvent o2) {
        RetroDofusCell currentPlayerPositionCell = CharacterState.getInstance().getCurrentPlayerCell();
        if (currentPlayerPositionCell == null) {
            return Integer.compare(o1.getRessourceCell().id(), o2.getRessourceCell().id());
        }
        int currentPlayerCellX = currentPlayerPositionCell.getAbscisse();
        int currentPlayerCellY = currentPlayerPositionCell.getOrdonnee();
        RetroRessourceCell nextTarget1 = o1.getRessourceCell();
        int nextTarget1X = nextTarget1.getAbscisse();
        int nextTarget1Y = nextTarget1.getOrdonnee();
        RetroRessourceCell nextTarget2 = o2.getRessourceCell();
        int nextTarget2X = nextTarget2.getAbscisse();
        int nextTarget2Y = nextTarget2.getOrdonnee();
        double distance1 = Math.round(Point2D.distance(currentPlayerCellX, currentPlayerCellY, nextTarget1X, nextTarget1Y));
        double distance2 = Math.round(Point2D.distance(currentPlayerCellX, currentPlayerCellY, nextTarget2X, nextTarget2Y));
        return Double.compare(distance1, distance2);
    }
}
