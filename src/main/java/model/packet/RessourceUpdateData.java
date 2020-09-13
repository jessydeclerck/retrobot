package model.packet;

import lombok.Getter;

@Getter
public class RessourceUpdateData {

    private final int cellId;

    private final RessourceStatus ressourceStatus;

    private boolean available;

    public RessourceUpdateData(int cellId, RessourceStatus ressourceStatus, boolean available) {
        this.cellId = cellId;
        this.ressourceStatus = ressourceStatus;
        this.available = available;
    }
}
