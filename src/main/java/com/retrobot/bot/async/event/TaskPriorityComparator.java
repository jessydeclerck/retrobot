package com.retrobot.bot.async.event;

import com.retrobot.bot.model.dofus.RetroDofusCell;
import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.bot.state.CharacterState;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class TaskPriorityComparator implements Comparator<RecolterTaskEvent> {

    private final CharacterState characterState;

    public TaskPriorityComparator(CharacterState characterState) {
        this.characterState = characterState;
    }

    @Override
    public int compare(RecolterTaskEvent o1, RecolterTaskEvent o2) {
        if (o1.getProcessCount() > 0) { // should put the event at the end of the queue
            return Integer.compare(o2.getProcessCount(), o1.getProcessCount());
        }
        RetroDofusCell currentTargetCell = characterState.getCurrentCellTarget();
        if (currentTargetCell == null) {
            return Integer.compare(o1.getRessourceCell().id(), o2.getRessourceCell().id());
        }
        double currentTargetCellX = currentTargetCell.getAbscisse();
        double currentTargetCellY = currentTargetCell.getOrdonnee();
        RetroRessourceCell nextTarget1 = o1.getRessourceCell();
        double nextTarget1X = nextTarget1.getAbscisse();
        double nextTarget1Y = nextTarget1.getOrdonnee();
        RetroRessourceCell nextTarget2 = o2.getRessourceCell();
        double nextTarget2X = nextTarget2.getAbscisse();
        double nextTarget2Y = nextTarget2.getOrdonnee();
        Double distance1 = Point2D.distanceSq(currentTargetCellX, currentTargetCellY, nextTarget1X, nextTarget1Y);
        Double distance2 = Point2D.distanceSq(currentTargetCellX, currentTargetCellY, nextTarget2X, nextTarget2Y);
        return distance1.compareTo(distance2);
    }
}
