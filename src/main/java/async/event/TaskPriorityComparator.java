package async.event;

import model.dofus.RetroDofusCell;
import model.dofus.RetroRessourceCell;
import state.CharacterState;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class TaskPriorityComparator implements Comparator<RecolterTaskEvent> {
    @Override
    public int compare(RecolterTaskEvent o1, RecolterTaskEvent o2) {
        RetroDofusCell currentCellTarget = CharacterState.getInstance().getCurrentCellTarget();
        if (currentCellTarget == null) {
            return Integer.compare(o1.getRessourceCell().id(), o2.getRessourceCell().id());
        }
        int currentTargetX = currentCellTarget.getAbscisse();
        int currentTargetY = currentCellTarget.getOrdonnee();
        RetroRessourceCell nextTarget1 = o1.getRessourceCell();
        int nextTarget1X = nextTarget1.getAbscisse();
        int nextTarget1Y = nextTarget1.getOrdonnee();
        RetroRessourceCell nextTarget2 = o2.getRessourceCell();
        int nextTarget2X = nextTarget2.getAbscisse();
        int nextTarget2Y = nextTarget2.getOrdonnee();
        int distance1 = (int) Math.round(Point2D.distance(currentTargetX, currentTargetY, nextTarget1X, nextTarget1Y));
        int distance2 = (int) Math.round(Point2D.distance(currentTargetX, currentTargetY, nextTarget2X, nextTarget2Y));
        return Integer.compare(distance1, distance2);
    }
}
