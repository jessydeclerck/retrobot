package com.retrobot.bot.processor.packet;

import lombok.Getter;

import static com.retrobot.bot.processor.packet.RessourceStatus.*;

@Getter
public class RessourceUpdateData {

    private final int cellId;

    private final RessourceStatus ressourceStatus;

    private boolean unknown;

    public RessourceUpdateData(int cellId, RessourceStatus ressourceStatus, boolean unknown) {
        this.cellId = cellId;
        this.ressourceStatus = ressourceStatus;
        this.unknown = unknown;
    }

    public boolean isAvailable() {
        if (BUSY.equals(ressourceStatus) || GONE.equals(ressourceStatus) || NOT_AVAILABLE.equals(ressourceStatus)) { //TODO to test
            return false;
        }
        return true;
    }

}
