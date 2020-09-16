package model.dofus;

import fr.arakne.utils.maps.serializer.CellData;
import lombok.Getter;

public class RetroTriggerCell extends RetroDofusCell {

    @Getter
    private final int nextMapId;

    @Getter
    private final int nextCellId;

    public RetroTriggerCell(RetroDofusMap map, CellData data, int id, int nextMapId, int nextCellId) {
        super(map, data, id);
        this.nextMapId = nextMapId;
        this.nextCellId = nextCellId;
    }
}
