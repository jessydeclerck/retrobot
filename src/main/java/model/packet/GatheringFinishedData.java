package model.packet;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class GatheringFinishedData extends PacketData {

    int playerId;
    int gatheredQty;

    public GatheringFinishedData(String fullPacket) {
        super(fullPacket);
        String[] gatheringFinishedData = fullPacket.replace("IQ", "").split("\\|");
        this.playerId = Integer.parseInt(gatheringFinishedData[0]);
        this.gatheredQty = Integer.parseInt(gatheringFinishedData[1]);
    }

    @Override
    public String getFullPacket() {
        return fullPacket;
    }

}
