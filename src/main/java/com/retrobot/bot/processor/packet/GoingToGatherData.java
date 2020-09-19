package com.retrobot.bot.processor.packet;

import com.retrobot.bot.model.dofus.RetroRessourceCell;
import com.retrobot.bot.state.MapState;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class GoingToGatherData extends PacketData {

    RetroRessourceCell cell;

    private MapState mapState;

    public GoingToGatherData(String fullPacket, MapState mapState) {
        super(fullPacket);
        this.mapState = mapState;
        int cellId = Integer.parseInt(fullPacket.replace("GA500", "").split(";")[0]);
        this.cell = this.mapState.getCurrentMap().getRessourceCell(cellId);
    }

}
