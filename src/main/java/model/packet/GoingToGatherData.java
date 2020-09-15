package model.packet;

import lombok.EqualsAndHashCode;
import lombok.Value;
import model.dofus.RetroRessourceCell;
import state.MapState;

@EqualsAndHashCode(callSuper = true)
@Value
public class GoingToGatherData extends PacketData {

    RetroRessourceCell cell;

    MapState mapState = MapState.getInstance();

    public GoingToGatherData(String fullPacket) {
        super(fullPacket);
        int cellId = Integer.parseInt(fullPacket.replace("GA500", "").split(";")[0]);
        this.cell = mapState.getCurrentMap().getRessourceCell(cellId);
    }

}
