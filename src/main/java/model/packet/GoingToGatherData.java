package model.packet;

import lombok.Data;
import model.dofus.RetroRessourceCell;
import state.MapState;

@Data
public class GoingToGatherData extends PacketData{

    private RetroRessourceCell cell;

    //GA500146;45
    public GoingToGatherData(String fullPacket) {
        super(fullPacket);
        int cellId = Integer.parseInt(fullPacket.replace("GA500", "").split(";")[0]);
        this.cell = MapState.getInstance().getCurrentMap().getRessourceCell(cellId);
    }

}
