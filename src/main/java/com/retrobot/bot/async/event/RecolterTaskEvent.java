package com.retrobot.bot.async.event;

import com.retrobot.bot.model.dofus.RetroRessourceCell;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RecolterTaskEvent {

    private final RetroBotTask retroBotTask = RetroBotTask.RECOLTER;

    private final RetroRessourceCell ressourceCell;

    private int processCount = 0; //number of time we tried to process this event

    public RecolterTaskEvent(RetroRessourceCell ressourceCell) {
        this.ressourceCell = ressourceCell;
    }

    public void incrementProcessCount() {
        processCount++;
    }

}
