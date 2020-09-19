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

    @EqualsAndHashCode.Exclude
    private final Class producer;

    private final RetroBotTask retroBotTask = RetroBotTask.RECOLTER;

    private final RetroRessourceCell ressourceCell;

    @EqualsAndHashCode.Exclude
    private int processCount = 0; //number of time we tried to process this event

    public RecolterTaskEvent(RetroRessourceCell ressourceCell, Class producer) {
        this.ressourceCell = ressourceCell;
        this.producer = producer;
    }

    public void incrementProcessCount() {
        processCount++;
    }

}
