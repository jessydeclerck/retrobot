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
        RetroDofusCell currentPlayerPositionCell = characterState.getCurrentPlayerCell();
        if (currentPlayerPositionCell == null) {
            return Integer.compare(o1.getRessourceCell().id(), o2.getRessourceCell().id());
        }
        double currentPlayerCellX = currentPlayerPositionCell.getWindowRelativeX();
        double currentPlayerCellY = currentPlayerPositionCell.getWindowRelativeY();
        RetroRessourceCell nextTarget1 = o1.getRessourceCell();
        double nextTarget1X = nextTarget1.getWindowRelativeX();
        double nextTarget1Y = nextTarget1.getWindowRelativeY();
        RetroRessourceCell nextTarget2 = o2.getRessourceCell();
        double nextTarget2X = nextTarget2.getWindowRelativeX();
        double nextTarget2Y = nextTarget2.getWindowRelativeY();
        double distance1 = Point2D.distance(currentPlayerCellX, currentPlayerCellY, nextTarget1X, nextTarget1Y);
        double distance2 = Point2D.distance(currentPlayerCellX, currentPlayerCellY, nextTarget2X, nextTarget2Y);
        return Double.compare(distance1, distance2);
    }
}
